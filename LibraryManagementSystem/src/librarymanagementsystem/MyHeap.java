package librarymanagementsystem;

public class MyHeap {
    private Book[] heap;
    private int size;
    
    public MyHeap(){
        this.heap = new Book[100];
        this.size = 0;
    }
    
    private void swap(int i, int j){
        Book temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    public void insert(Book book){
        if(size == heap.length){
            return;
        }
        heap[size] = book;
        int temp = size;
        size++;
        
        while(temp > 0 && heap[temp].getBorrowCount() > heap[(temp -1)/2].getBorrowCount()){
            swap(temp, (temp -1)/2);
            temp = (temp -1)/2;
        }
    }
    
    public Book extractMax(){
        if(size == 0)
            return null;
        Book maxBook = heap[0];
        heap[0] = heap[size-1];
        size--;
        heapify(0);
        return maxBook;
    }
    
    public void heapify(int i){
        int largest = i;
        int left = 2*i+1;
        int right = 2*i+2;
        
        if(left < size && heap[left].getBorrowCount() > heap[largest].getBorrowCount()){
            largest = left;
        }
        if(right < size && heap[right].getBorrowCount() > heap[largest].getBorrowCount()){
            largest = right;
        }
        if(largest != i){
            swap(i, largest);
            heapify(largest);
        }
    }
    
    public int getSize(){
        return size;
    }
    
    public Book[] getTopBooks(){
        Book[] topBooks = new Book[size];
        for(int i = 0; i<size; i++){
            topBooks[i] = heap[i];
        }
        return topBooks;
    }
}
