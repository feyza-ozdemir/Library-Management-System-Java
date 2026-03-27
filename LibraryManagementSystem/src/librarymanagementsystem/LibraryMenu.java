
//Preparer: Feyza Özdemir - 123456789


package librarymanagementsystem;

import java.util.Scanner;

public class LibraryMenu {

    private LibrarySystem system;
    private Scanner sc;
    
    public LibraryMenu(){
        system = new LibrarySystem();
        sc = new Scanner(System.in);
    }
    
    public void displayMenu(){
        System.out.println("\n--- Library Management System---");
        System.out.println("1- Add Book");
        System.out.println("2- Remove Book");
        System.out.println("3- Register Member");
        System.out.println("4- Remove Member");
        System.out.println("5- Borrow Book");
        System.out.println("6- Return Book");
        System.out.println("7- Search Book");
        System.out.println("8- Search Member");
        System.out.println("9- Most Popular Books");
        System.out.println("10- Undo");
        System.out.println("0- Exit");
        System.out.println("Select[0-10]");
    }
    
    public void run(){
        int choice;
        do{
            displayMenu();
            try{
                String input = sc.nextLine();
                choice = Integer.parseInt(input);
                System.out.println("-------------------------");
                switch(choice){
                    case 1: system.addBook();
                            break;
                    case 2: system.removeBook();
                            break;
                    case 3: system.registerMember();
                            break;
                    case 4: system.removeMember();
                            break;
                    case 5: system.borrowBook();
                            break;
                    case 6: system.returnBook();
                            break;
                    case 7: system.searchBookByTitle();
                            break;
                    case 8: system.searchMember();
                            break;
                    case 9: system.generatePopularityReport();
                            break;
                    case 10: system.undo();
                            break;
                    case 0: System.out.println("Exiting the system.");
                            break;
                    default: System.out.println("Invalid selection. Please try again.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Error: Please enter a valid number.");
                choice = -1;
            }
            catch(Exception e){
                System.out.println("" + e.getMessage());
                choice = -1;
            }
        }
        while(choice != 0);
        sc.close();
    }
    public static void main(String[] args) {
        LibraryMenu menu = new LibraryMenu();
        menu.run();
    }

}