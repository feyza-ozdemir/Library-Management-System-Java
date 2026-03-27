package librarymanagementsystem;

public class MyBST {
    private TreeNode root;
    
    public MyBST(){
        this.root = null;
    }
    
    public void insert(Book data){
        root = insertRecursive(root, data);
    }
    
    private TreeNode insertRecursive(TreeNode temp, Book data){
        if(temp == null){
            return new TreeNode(data);
        }
        
        int comparison = data.getTitle().compareTo(temp.data.getTitle());
        
        if(comparison < 0){
            temp.left = insertRecursive(temp.left, data);
        }
        else if(comparison > 0){
            temp.right = insertRecursive(temp.right, data);
        }
        return temp;
    }

    public Book search(String title){
        return searchRecursive(root, title);
    }
    
    private Book searchRecursive(TreeNode temp, String title){
        if(temp == null){
            return null;
        }
        
        int comparison = title.compareTo(temp.data.getTitle());
        
        if(comparison == 0){
            return temp.data;
        }
        if(comparison < 0){
            return searchRecursive(temp.left, title);
        }
        else{
            return searchRecursive(temp.right, title);
        }
    }
    
    public void delete(String title){
        root = deleteRecursive(root, title);
    }
    
    private TreeNode deleteRecursive(TreeNode temp, String title){
        if(temp == null)
            return null;
        int comparison = title.compareTo(temp.data.getTitle());
        if(comparison < 0){
            temp.left = deleteRecursive(temp.left,  title);
        }
        else if(comparison > 0){
            temp.right = deleteRecursive(temp.right, title);
        }
        else{
            if(temp.left == null)
                return temp.right;
            if(temp.right == null)
                return temp.left;
            
            temp.data = findSmallest(temp.right);
            temp.right = deleteRecursive(temp.right, temp.data.getTitle());
        }
        return temp;
    }
    
    private Book findSmallest(TreeNode root){
        Book smallest = root.data;
        while(root.left != null){
            smallest = root.left.data;
            root = root.left;
        }
        return smallest;
    }
    
    public void inorder(){
        inorderRecursive(root);
    }
    private void inorderRecursive(TreeNode temp){
        if(temp != null){
            inorderRecursive(temp.left);
            System.out.println(temp.data.toString());
            inorderRecursive(temp.right);
        }
    }
}
