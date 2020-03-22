package cli;

import java.util.*;
import java.sql.*;
import models.*;

public class AdminCLI implements CLIInterface {
    private Database db;
    private Scanner sc;

    public AdminCLI(Database db, Scanner sc) {
        this.db = db;
        this.sc = sc;
    }

    public void startCLI() {
        while (true) {
            printMenu();
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1: optCreateAllTables(); break;
                case 2: optDeleteAllTables(); break;
                case 3: optLoadFromDatafile(); break;
                case 4: optShowNumOfRecords(); break;
                case 5: return;
                default: System.out.println("[Error] Invalid operation, choose again.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("-----Operations for administrator menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Create all tables");
        System.out.println("2. Delete all tables");
        System.out.println("3. Load from datafile");
        System.out.println("4. Show number of records in each table");
        System.out.println("5. Return to the main menu");
        System.out.printf("Enter your choice: ");
    }

    private void optCreateAllTables() {
        try {
            System.out.printf("Processing...");
            db.createAllTables();
            System.out.println("Done! Database is initialized!\n");
        } catch (SQLException e) {
            if (e.toString().contains("exists")) {
                System.out.println("[Error] Tables already created.\n");
            } else {
                System.out.println("[Error] Failed to create tables.\n");
            }
        }
    }

    private void optDeleteAllTables() {
        try {
            System.out.println("Processing...");
            db.deleteAllTables();
            System.out.println("Done! Database is removed!\n");
        } catch (SQLException e) {
            if (e.toString().contains("Unknown")) {
                System.out.println("[Error] Tables do not exist.\n");
            } else {
                System.out.println("[Error] Failed to delete tables.\n");
            }
        }
    }

    private void optLoadFromDatafile() {
        System.out.printf("Type in the Source Data Folder Path: ");
        String folderPath = sc.next();
        try {
            System.out.printf("Processing...");
            db.loadDataFromFiles(folderPath);
            System.out.println("Done! Data is inputted to the database!\n");
        } catch (Exception e) {
            System.out.println("[Error] Cannot load data from file.\n");
        }
    }

    private void optShowNumOfRecords() {
        System.out.println("Number of records in each table:");
        db.countAndPrintAllRecordsInTables();
    }
}

