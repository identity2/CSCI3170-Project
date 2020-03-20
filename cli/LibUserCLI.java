package cli;

import java.util.*;
import models.*;

public class LibUserCLI implements CLIInterface {
    private Database db;
    private Scanner sc;

    public LibUserCLI(Database db, Scanner sc) {
        this.db = db;
        this.sc = sc;
    }

    public void startCLI() {
        while (true) {
            printMenu();
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1: optSearchForBooks(); break;
                case 2: optShowLoadRecord(); break;
                case 3: return;
                default: System.out.println("[Error] Invalid operation, choose again.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("-----Operations for library user menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Search for Books");
        System.out.println("2. Show load record of a user");
        System.out.println("3. Return to the main menu");
        System.out.printf("Enter your choice: ");
    }

    private void optSearchForBooks() {
        // TODO
    }

    private void optShowLoadRecord() {
        // TODO
    }
}