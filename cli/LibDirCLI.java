package cli;

import java.util.*;
import models.*;

public class LibDirCLI implements CLIInterface {
    private Database db;
    private Scanner sc;

    public LibDirCLI(Database db, Scanner sc) {
        this.db = db;
        this.sc = sc;
    }

    public void startCLI() {
        while (true) {
            printMenu();
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1: optListAllUnreturned(); break;
                case 2: return;
                default: System.out.println("[Error] Invalid operation, choose again.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("-----Operations for library director menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. List all un-returned book copies which are checked-out within a period");
        System.out.println("2. Return to the main menu");
        System.out.printf("Enter your choice: ");
    }

    private void optListAllUnreturned() {
        Calendar startDate, endDate;

        System.out.printf("Type in the starting date [dd/mm/yyyy]: ");
        startDate = DateConv.strToCal(sc.next());
        System.out.printf("Type in the ending date [dd/mm/yyyy]: ");
        endDate = DateConv.strToCal(sc.next());
        System.out.println();

        db.listAllUnreturnedBooks(startDate, endDate);
    }
}