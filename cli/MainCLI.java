package cli;

import java.util.Scanner;
import models.*;

public class MainCLI implements CLIInterface{
    private Database db;
    private Scanner sc;

    public MainCLI(Database db) {
        this.db = db;
        this.sc = new Scanner(System.in);
    }

    public void startCLI() {
        while (true) {
            printMainMenu();
            int choice = sc.nextInt();
            System.out.println();
            CLIInterface c = null;
            switch (choice) {
                case 1: c = new AdminCLI(db, sc); break;
                case 2: c = new LibUserCLI(db, sc); break;
                case 3: c = new LibrarianCLI(db, sc); break;
                case 4: c = new LibDirCLI(db, sc); break;
                case 5: return;
                default: System.out.println("[Error] Invalid operation, choose again.\n");
            }

            if (c != null) c.startCLI();
        }
    }

    private void printMainMenu() {
        System.out.println("-----Main menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Operations for administrator");
        System.out.println("2. Operations for library user");
        System.out.println("3. Operations for librarian");
        System.out.println("4. Operations for library director");
        System.out.println("5. Exit this program");
        System.out.printf("Enter your choice: ");
    }
}