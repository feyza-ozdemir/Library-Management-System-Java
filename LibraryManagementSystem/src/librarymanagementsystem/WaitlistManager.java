package librarymanagementsystem;

public class WaitlistManager {
    private MyHashTable<Integer, MyQueue<Integer>> waitlists;
    
    public WaitlistManager(){
        this.waitlists = new MyHashTable<>(100);
    }
    
    public void addToWaitlist(int bookID, int memberID){
        MyQueue<Integer> queue = waitlists.get(bookID);
        if(queue == null){
            queue = new MyQueue<>();
            waitlists.put(bookID, queue);
        }
        queue.enqueue(memberID);
        System.out.println("Member " + memberID + " was added the waitlist of Book "+ bookID);
    }
    
    public Integer getNext(int bookID){
        MyQueue<Integer> queue = waitlists.get(bookID);
        if(queue == null || queue.isEmpty())
            return null;
        return queue.dequeue();
    }
    
    public boolean isEmpty(int bookID){
        MyQueue<Integer> queue = waitlists.get(bookID);
        return queue == null || queue.isEmpty();
    }
}
