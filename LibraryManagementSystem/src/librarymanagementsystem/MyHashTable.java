package librarymanagementsystem;

public class MyHashTable<Key, Value> {
    private HashEntry<Key, Value>[] table;
    private int size;
    private int capacity;
    private static final int STUDENT_ID = 230315019;
    
    public MyHashTable(int size){
        this.capacity = size;
        this.table = new HashEntry[capacity];
    }
    
    //Calculates the hash index using the Student ID(230315019)
    private int hash(Key key){
        if(key == null)
            return 0;
        int hashCode = key.hashCode();
        int index = (hashCode * STUDENT_ID) % capacity;
        return (index<0) ? (index + capacity) : index;
    }
    
    public void put(Key key, Value value){
        int index = hash(key);
        HashEntry<Key, Value> newEntry = new HashEntry<>(key, value);
        
        if(table[index] == null){
            table[index] = newEntry;
        }
        else{
            HashEntry<Key, Value> temp = table[index];
            HashEntry<Key, Value> prev = null;
            
            while(temp != null){
                if(temp.key.equals(key)){
                    temp.value = value;
                    return;
                }
                prev = temp;
                temp = temp.next;
            }
            prev.next = newEntry;
        }
        size++;
    }
    
    public Value get(Key key){
        int index = hash(key);
        HashEntry<Key, Value> temp = table[index];
        
        while(temp != null){
            if(temp.key.equals(key)){
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    //Removes an entry from the Hash Table using chaining to handle collisions.
    public void remove(Key key){
        if(key == null)
            return;
        int index = hash(key);
        HashEntry<Key, Value> temp = table[index];
        HashEntry<Key, Value> prev = null;
        
        while(temp != null){
            if(temp.key.equals(key)){
                if(prev == null){
                    table[index] = temp.next;
                }
                else{
                    prev.next = temp.next;
                }
                size--;
                return;
            }
            prev = temp;
            temp = temp.next;
        }
    }
    
    public int getSize(){
        return size;
    }
    
    public int getCapacity(){
        return capacity;
    }
}