public class CLI {
    private Database db;

    public CLI(Database db) {
        this.db = db;
    }

    private void printMainMenu() {
        System.out.println("-----Main menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Operations for administrator");
        System.out.println("2. Operations for library user");
        System.out.println("3. Operations for librarian");
        System.out.println("4. Operations for library director");
        System.out.println("5. Exit this program");
    }

    private void printAdminMenu() {
        System.out.println("-----Operations for administrator menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Create all tables");
        System.out.println("2. Delete all tables");
        System.out.println("3. Load from datafile");
        System.out.println("4. Show number of records in each table");
        System.out.println("5. Return to the main menu");
    }

    private void printLibUserMenu() {
        System.out.println("-----Operations for library user menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Search for Books");
        System.out.println("2. Show load record of a user");
        System.out.println("3. Return to the main menu");
    }

    private void printLibrarianMenu() {
        System.out.println("-----Operations for librarian menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Book Borrowing");
        System.out.println("2. Book Returning");
        System.out.println("3. Return to the main menu");
    }

    private void printLibDirMenu() {
        System.out.println("-----Operations for library director menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. List all un-returned book copies which are checked-out within a period");
        System.out.println("2. Return to the main menu");
    }
}