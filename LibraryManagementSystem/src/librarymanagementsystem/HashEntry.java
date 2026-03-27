package librarymanagementsystem;

public class HashEntry<Key, Value> {
    Key key;
    Value value;
    HashEntry<Key, Value> next;
    
    public HashEntry(Key key, Value value){
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
