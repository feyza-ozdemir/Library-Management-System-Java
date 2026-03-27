package librarymanagementsystem;

public class Member {
    
    int memberID;
    String name;
    
    MyLinkedList<Integer> borrowedBooks;

    public Member(int memberID, String name) {
        this.memberID = memberID;
        this.name = name;
        this.borrowedBooks = new MyLinkedList<>();
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyLinkedList<Integer> getBorrowedBooks() {
        return borrowedBooks;
    }
    
    public void addBorrowed(int bookID){
        borrowedBooks.add(bookID);
    }
    
    public void removeBook(int BookID){
        borrowedBooks.remove(BookID);
    }
    
    @Override
    public String toString(){
        return "ID: " + memberID + ", Name: " + name + ", Number of Book Borrowed: " + borrowedBooks.getSize();
    }
}
