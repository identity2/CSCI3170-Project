package cli;

import java.util.*;
import models.*;

public class LibrarianCLI implements CLIInterface {
    private Database db;
    private Scanner sc;

    public LibrarianCLI(Database db, Scanner sc) {
        this.db = db;
        this.sc = sc;
    }

    public void startCLI() {
        while (true) {
            printMenu();
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1: optBookBorrowing(); break;
                case 2: optBookReturning(); break;
                case 3: return;
                default: System.out.println("[Error] Invalid operation, choose again.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("-----Operations for librarian menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Book Borrowing");
        System.out.println("2. Book Returning");
        System.out.println("3. Return to the main menu");
        System.out.printf("Enter your choice: ");
    }

    private void optBookBorrowing() {
        String userID, callnum;
        int copynum;
        System.out.printf("Enter The User ID: ");
        userID = sc.next();
        System.out.printf("Enter The Call Number: ");
        callnum = sc.next();
        System.out.printf("Enter The Copy Number: ");
        copynum = sc.nextInt();
        System.out.println();

        db.borrowBook(userID, callnum, copynum);
    }

    private void optBookReturning() {
        String userID, callnum;
        int copynum;
        System.out.printf("Enter The User ID: ");
        userID = sc.next();
        System.out.printf("Enter The Call Number: ");
        callnum = sc.next();
        System.out.printf("Enter The Copy Number: ");
        copynum = sc.nextInt();
        System.out.println();

        db.returnBook(userID, callnum, copynum);
    }
}