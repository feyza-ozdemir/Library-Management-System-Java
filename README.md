# Library Management System - Data Structures Project 📚
This project was developed for the CSE 2105 Data Structures course at Manisa Celal Bayar University - Computer Engineering Department. It is a robust library automation system designed to handle complex data relationships—such as book/member management, queue handling, and transaction history—with maximum efficiency.
## 🛠️ Data Structures & Design Decisions
The core of the system is built upon specialized data structures, each optimized for specific functional requirements:
* **Hash Table (Separate Chaining):** Serves as the primary storage layer for rapid ID-based retrieval of books and members. It implements "Separate Chaining" to handle collisions effectively.
* **Binary Search Tree (BST):** Facilitates efficient alphabetical searching by book titles. It reduces search time to logarithmic complexity by eliminating half of the search space at each step.
* **Max-Heap (Priority Queue):** Powers the 'Popularity Report' feature. It automatically reorganizes itself whenever a book is borrowed, ensuring the most popular book is always at the root.
* **Stack:** Implements the 'Undo' functionality following the LIFO (Last-In-First-Out) principle. Each operation is encapsulated as an Action object.
* **Queue:** Manages book waitlists based on the FIFO (First-In-First-Out) principle. When a book is returned, the system automatically identifies and assigns it to the next member in the queue.
* **Singly Linked List:** Manages the dynamic history of borrowed books within each member's profile, ensuring efficient memory usage.
## ✨ Key Features
* **Recursive Undo Logic:** A specialized mechanism that distinguishes between manual actions and system-generated assignments. For instance, if a book return triggers an automatic assignment to a waitlisted member, a single "Undo" will recursively revert both actions.
* **Data Integrity Protection:** The system prevents the deletion of members with active loans to avoid orphaned records and maintain database consistency.
* **Input Validation:** Robust error handling using try-catch blocks to prevent system crashes from invalid user inputs.
* **Optimized Performance:** Key operations are optimized for speed: $O(1)$ for ID-based registration/removal, $O(\log n)$ for title searches, and $O(n \log n)$ for full popularity reports. 
## 💻 Installation & Usage
1. Clone this repository: git clone https://github.com/feyza-ozdemir/Library-Management-System-Java.git
2. Open the project in Apache NetBeans or your preferred Java IDE.
3. Run the LibraryMenu.java file to start the console application.
4. Navigate through the system using the numeric menu options.