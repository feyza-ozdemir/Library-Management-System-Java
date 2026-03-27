package librarymanagementsystem;

public class MyStack<Type> {
    private Node<Type> top;
    
    public MyStack(){
        this.top = null;
    }
    
    public void push(Type data){
    Node<Type> newNode = new Node<>(data);
    newNode.next = top;
    top = newNode;
    }
    
    public Type pop(){
        if(isEmpty()){
            return null;
        }
        Type data = top.data;
        top = top.next;
        return data;
    }
    
    public Type peek(){
        if(isEmpty())
            return null;
        return top.data;
    }
    
    public boolean isEmpty(){
        return top == null;
    }
}
