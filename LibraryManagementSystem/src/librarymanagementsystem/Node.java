package librarymanagementsystem;

public class Node<Type> {
    Type data;
    Node<Type> next;
    
    public Node(Type data){
        this.data = data;
        this.next = null;
    }
}
