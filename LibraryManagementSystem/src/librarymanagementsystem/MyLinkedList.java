package librarymanagementsystem;

public class MyLinkedList<Type> {
    private Node<Type> head;
    private int size;
    
    public MyLinkedList(){
        this.head = null;
        this.size = 0;
    }
    
    public void add(Type data){
        Node<Type> newNode = new Node<>(data);
        if(head == null){
            head = newNode;
            size++;
            return;
        }
        Node<Type> temp = head;
        while(temp.next != null)
            temp = temp.next;
        temp.next = newNode;
        size++;
    }
    
    public void remove(Type data){
        if(head == null)
            return;
        if(head.data.equals(data)){
            head = head.next;
            size--;
            return;
        }
        
        Node<Type> temp = head;
        while(temp.next != null && !temp.next.data.equals(data)){
            temp = temp.next;
        }
            if(temp.next != null){
                temp.next = temp.next.next;
                size--;
            }
            
    }
    
    public boolean contains(Type data){
        Node<Type> temp = head;
        while(temp != null){
            if(temp.data.equals(data)){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    
    public Node<Type> getHead(){
        return head;
    }
    
    public int getSize(){
        return size;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
}
