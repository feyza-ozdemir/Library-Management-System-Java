package librarymanagementsystem;

import java.util.Scanner;

public class LibrarySystem {
    private MyHashTable<Integer, Book> books;
    private MyHashTable<Integer, Member> members;
    private MyStack<Action> undoStack;
    private MyBST booksByTitle;
    private MyHeap popularBooksHeap;
    private WaitlistManager waitlistManager;
    private Scanner sc;
    
    private int nextBookID = 1000;
    private int nextMemberID = 5000;
    
    public LibrarySystem(){
        books = new MyHashTable<>(200);
        members = new MyHashTable<>(100);
        undoStack = new MyStack<>();
        booksByTitle = new MyBST();
        popularBooksHeap = new MyHeap();
        waitlistManager = new WaitlistManager();
        this.sc = new Scanner(System.in);
    }
    
    public void addBook(){
        System.out.print("Book Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        if(title.isEmpty() || author.isEmpty()){
            System.out.println("\nError: ");
            return;
        }
        int newID = nextBookID++;
        Book newBook = new Book(newID, title, author);
        books.put(newID, newBook);
        booksByTitle.insert(newBook);
        undoStack.push(new Action(ActionType.ADD_BOOK, 0, newID, newBook, false));
        System.out.println("\nSuccess: Book ' " + title + " ' added with ID " + newID + ".");
    }
    
    public void removeBook(){
        System.out.print("ID of the book to be removed: ");
        try{
            int bookID = Integer.parseInt(sc.nextLine());
            Book bookToRemove = books.get(bookID);
            if(bookToRemove == null){
                System.out.println("\nError: Book not found.");
                return;
            }
            if(!bookToRemove.isAvailable()){
                System.out.println("\nError: The book cannot be deleted because it is currently on loan.");
                return;
            }
        books.remove(bookID);
        booksByTitle.delete(bookToRemove.getTitle());
        undoStack.push(new Action(ActionType.REMOVE_BOOK, 0, bookID, bookToRemove, false));
        System.out.println("\nSuccess: Book ID ' " + bookID + " ' has been removed from the catalog.");
        }
        catch(Exception e){
            System.out.println("\nError: Invalid ID");
        }
    }
    
    public void registerMember(){
        System.out.print("Member Name: ");
        String name = sc.nextLine();
        int newID = nextMemberID++;
        Member newMember = new Member(newID, name);
        members.put(newID, newMember);
        undoStack.push(new Action(ActionType.REGISTER_MEMBER, newID, 0, newMember, false));
        System.out.println("\nSuccess: Member ' " + name + " ' registered with ID " + newID + ".");
    }
    
    public void removeMember(){
        try{
            System.out.print("ID of the member to be removed: ");
            int memberIDRemove = Integer.parseInt(sc.nextLine());
            Member memberToRemove = members.get(memberIDRemove);
            if(memberToRemove == null){
                System.out.println("\nError: Member not found.");
                return;
            }
            //Prevent deletion if the member has active loans to avoid orphaned bookrecords.
            if(!memberToRemove.getBorrowedBooks().isEmpty()){
                System.out.println("\nError: The member has borrowed books; these cannot be deleted.");
                return;
            }
            members.remove(memberIDRemove);
            undoStack.push(new Action(ActionType.REMOVE_MEMBER, memberIDRemove, 0, memberToRemove, false));
            System.out.println("\nSuccess: Member ID ' " + memberIDRemove + " ' removed.");
        }
        catch(NumberFormatException e){
            System.out.println("\nError: Please enter a valid ID number");
        }
        
    }
    
    public void borrowBook(){
        System.out.print("Member ID: ");
        int memberIDBorrow = Integer.parseInt(sc.nextLine());
        System.out.print("Book ID: ");
        int bookIDBorrow = Integer.parseInt(sc.nextLine());
        Book book = books.get(bookIDBorrow);
        Member member = members.get(memberIDBorrow);
        if(book == null || member == null){
            System.out.println("\nBook or Member not found.");
            return;
        }
        if(book.isAvailable()){
            book.setAvailable(false);
            member.addBorrowed(bookIDBorrow);
            book.incrementBorrowCount();
            popularBooksHeap.insert(book);
            undoStack.push(new Action(ActionType.BORROW_BOOK, memberIDBorrow, bookIDBorrow, book, false));
            System.out.println("\nSuccess: Book ' " + book.getTitle() + " ' lent to " + member.getName() + ".");
        }
        else{
            waitlistManager.addToWaitlist(bookIDBorrow, memberIDBorrow);
            System.out.println("\nBook unavailable. Member " + memberIDBorrow + " added to the waitlist fot Book " + bookIDBorrow + ".");
        }
    }
    
    public void returnBook(){
        System.out.println("Member ID: ");
        int memberIDReturn = Integer.parseInt(sc.nextLine());
        System.out.println("Book ID: ");
        int bookIDReturn = Integer.parseInt(sc.nextLine());
        Book book = books.get(bookIDReturn);
        Member member = members.get(memberIDReturn);
        
        if(book == null || member == null){
            System.out.println("\nBook or Member not found.");
            return;
        }
        if(!member.getBorrowedBooks().contains(bookIDReturn)){
            System.out.println("\nError: No record was found of the member borrowing this book.");
            return;
        }
        member.removeBook(bookIDReturn);
        undoStack.push(new Action(ActionType.RETURN_BOOK, memberIDReturn, bookIDReturn, book, false));
        Integer nextMemberID = waitlistManager.getNext(bookIDReturn);
        if(nextMemberID != null){
            Member nextMember = members.get(nextMemberID);
            book.setAvailable(false);
            nextMember.addBorrowed(bookIDReturn);
            book.incrementBorrowCount();
            popularBooksHeap.insert(book);
            undoStack.push(new Action(ActionType.BORROW_BOOK, nextMemberID, bookIDReturn, book, true));
            System.out.println("\nSuccess: Book ' " + bookIDReturn + " ' returned and asigned to next member in waitlist ' " + nextMember.getName() + " '.");
        }
        else{
            book.setAvailable(true);
            System.out.println("\nSuccess: Book " + bookIDReturn +" is now available in the catalog.");
        }
    }
    
    public Book searchBookByTitle(){
        System.out.print("Title of the book to be searched: ");
        String searchTitle = sc.nextLine().trim();
        Book foundBook = booksByTitle.search(searchTitle);
        if(foundBook != null){
            System.out.println("\nBook was found: " + foundBook.toString());
        }
        else{
            System.out.println("\nError: No book with the title " + searchTitle + " was found.");
        }
        return foundBook;
    }
    
    public void searchMember(){
        try{
            System.out.print("Enter Member ID to search: ");
            int memberID = Integer.parseInt(sc.nextLine());
            Member member = members.get(memberID);
            if(member == null){
                System.out.println("\nError: Member with ID " + memberID + " not found.");
                return;
            }
            System.out.println("\n---Member Profile---");
            System.out.println(member.toString());
            MyLinkedList<Integer> list = member.getBorrowedBooks();
            Node<Integer> temp = list.getHead();
            if(temp == null){
                System.out.println("No books currently on loan.");
            }
            else{
                System.out.println("Borrowed Books Detail List:");
                int count = 1;
                while(temp != null){
                    int bookID = temp.data;
                    
                    Book b = books.get(bookID);
                    if(b != null){
                        System.out.println(count + ". [ID: " + bookID + "] Title: " + b.getTitle());
                    }
                    else{
                        System.out.println(count + ". [" + bookID + "] Info: Catalog data missing.");
                    }
                    temp = temp.next;
                    count++;
                }
            }
            System.out.println("----------------------");
        }
        catch(NumberFormatException e){
            System.out.println("\nError: Please enter a valid Member ID.");
        }
    }
    
    public void undo(){
        if(undoStack.isEmpty()){
            System.out.println("\nError: No actions were found to undo (Stack is empty).");
            return;
        }
        
        Action lastAction = undoStack.pop();
        
        switch(lastAction.getType()){
            case ActionType.ADD_BOOK :  Book bookAdded = (Book) lastAction.getPayload();
                                        books.remove(lastAction.getBookID());
                                        booksByTitle.delete(bookAdded.getTitle());
                                        System.out.println("Undo Successful: Book ' " + lastAction.getBookID()+ " ' removed.");
                                        break;
            case ActionType.REMOVE_BOOK :   Book bookRemoved = (Book) lastAction.getPayload();
                                            books.put(lastAction.getBookID(), bookRemoved);
                                            booksByTitle.insert(bookRemoved);
                                            System.out.println("Undo Successful: Book ' " + lastAction.getBookID() + " ' added again.");
                                            break;
            case ActionType.REGISTER_MEMBER :   Member memberRegistered = (Member) lastAction.getPayload();
                                                members.remove(lastAction.getMemberID());
                                                System.out.println("Undo Successful: Member ' " + lastAction.getMemberID() + " ' removed");
                                                break;
            case ActionType.REMOVE_MEMBER : Member memberRemoved = (Member) lastAction.getPayload();
                                            members.put(lastAction.getMemberID(), memberRemoved);
                                            System.out.println("Undo Successful: Book ' " + lastAction.getMemberID() + " ' registered again.");
                                            break;
            case ActionType.BORROW_BOOK :   Book borrowedBook = books.get(lastAction.getBookID());
                                            Member borrowerMember = members.get(lastAction.getMemberID());
                                            if(borrowedBook != null && borrowerMember!= null){
                                                //Normal borrow: simply make the book available on the catalog again.
                                                borrowedBook.setAvailable(true);
                                                borrowerMember.removeBook(lastAction.getBookID());
                                                borrowedBook.decrementBorrowCount();
                                                popularBooksHeap.insert(borrowedBook);
                                                System.out.println("Undo Successful: Book ' " + lastAction.bookID + " ' is back in the catalog.");
                                                //Check if this borrow was an automatic system assignment from waitlist.
                                                if(lastAction.isAutoAssignment()){
                                                    //Re-insert the member action is the return that triggered this assignment,
                                                    //recursively undo it to return the book to the original owner.
                                                    waitlistManager.addToWaitlist(borrowedBook.getBookID(), borrowerMember.getMemberID());
                                                    System.out.println("Undo: " + borrowerMember.getName() + " moved back to waitlist for Book " + borrowedBook.bookID);
                                                    if(!undoStack.isEmpty() && undoStack.peek().getType() == ActionType.RETURN_BOOK){
                                                        System.out.println("System detected linked return. Reverting that too");
                                                        undo();
                                                    }
                                                }
                                            }
                                            break;
            case ActionType.RETURN_BOOK :   Book returnedBook = books.get(lastAction.getBookID());
                                            Member returnerMember = members.get(lastAction.getMemberID());
                                            if(returnedBook != null && returnerMember != null){
                                                returnedBook.setAvailable(false);
                                                returnerMember.addBorrowed(lastAction.getBookID());
                                                System.out.println("Undo Successful: Return canceled, Book ID: " + lastAction.getBookID() + " ' is back with " + returnerMember.getName() + ".");
                                            }
                                            else{
                                                System.out.println("Error: ...");
                                            }
                                            break;
            default :   System.out.println("Error: ...");
                        break;
        }
    }
    
    public void generatePopularityReport(){
        System.out.println("\n--- Most Popular Books ---");
        if(popularBooksHeap.getSize() == 0){
            System.out.println("No borrowing data available yet.");
            return;
        }
        //I use a temporary heap to extract values without destroying the original ppopularity data. 
        MyHeap tempHeap = new MyHeap();
        Book[] tempBooks = popularBooksHeap.getTopBooks();
        for(Book b : tempBooks){
            if(b != null){
                tempHeap.insert(b);
            }
        }
        //lastPirnted prevents displaying the same book multiple times if it was borrowed multiple times.
        int lastPrintedID = -1;
        int printedCount = 0;
        
        while(printedCount < 10){
            Book popular = tempHeap.extractMax();
            
            if(popular == null)
                break;
            
            if(popular.getBookID() != lastPrintedID){
                printedCount++;
                System.out.println(printedCount + ". " + popular.getTitle() + " (ID: " + popular.getBookID() + ") - Boorow Count: " + popular.getBorrowCount());
                lastPrintedID = popular.getBookID();
            }
        }
    }
}
