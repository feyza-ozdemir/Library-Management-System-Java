package librarymanagementsystem;

public class Book {

    int bookID;
    String title;
    String author;
    boolean isAvailable;
    int borrowCount;

    public Book(int bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowCount = 0;
    }
    
    
    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }
    
    //Ödünç alma sayısını artır
    public void incrementBorrowCount(){
        this.borrowCount++;
    }
    public void decrementBorrowCount(){
        if(this.borrowCount > 0){
            this.borrowCount--;
        }
    }
    
    @Override
    public String toString(){
        return "ID: " + bookID + ", Title: " + title + ", Author: " + author + ", Status: " + (isAvailable ? "Available" : "Borrowed" + ", Borrow Count: " + borrowCount);
     }
    
}
