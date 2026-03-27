package librarymanagementsystem;

public class MyQueue<Type>{
    private Node<Type> front;
    private Node<Type> rear;

    public MyQueue(){
        this.front = null;
        this.rear = null;
    }
    
    public void enqueue(Type data){
        Node<Type> newNode = new Node<>(data);
        if(isEmpty()){
            front = newNode;
            rear = newNode;
        }
        else{
            rear.next = newNode;
            rear = newNode;
        }
    }
   
    public Type dequeue(){
        if(isEmpty())
            return null;
        Type data = front.data;
        front = front.next;
        if(front == null){
            rear = null;
        }
        return data;
    }
    
    public boolean isEmpty(){
        return front == null;
    }
}
