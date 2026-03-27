package librarymanagementsystem;

public class Action {
    public int type;
    public int memberID;
    public int bookID;
    private Object payload;
    //Flag to distinguish between manual borrows and system-generated waitlist assignments.
    //This essential for the recursive undo logic.
    private boolean isAutoAssignment;
    
    public Action(int type, int memberID, int bookID, Object payload, boolean isAutoAssignment) {
        this.type = type;
        this.memberID = memberID;
        this.bookID = bookID;
        this.payload = payload;
        this.isAutoAssignment = isAutoAssignment;
    }
    
    public Action(int type, int memberID, int bookID){
        this(type, memberID, bookID, null, false);
    }
    
    
    public int getType() {
        return type;
    }

    public int getMemberID() {
        return memberID;
    }

    public int getBookID() {
        return bookID;
    }

    public Object getPayload(){
        return payload;
    }
    
    public boolean isAutoAssignment() {
        return isAutoAssignment;
    }
    
    @Override
    public String toString(){
        String actionName = switch(type){
            case ActionType.ADD_BOOK -> "Add Book";
            case ActionType.REMOVE_BOOK -> "Remove Book";
            case ActionType.REGISTER_MEMBER -> "Register Member";
            case ActionType.REMOVE_MEMBER -> "Remove Member";
            case ActionType.BORROW_BOOK -> "Borrow Book";
            case ActionType.RETURN_BOOK -> "Return Book";
            default -> "Unknown Process";
        };
        return actionName + " (Book ID: " + bookID + ", Member ID: " + memberID + ")";
    }
}
