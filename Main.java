import java.sql.*;

public class Main {

    public static void main(String[] args) {
        // Connecting to the database.
        Database db = new Database();
        try {
            db.connect();
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: Java MySQL DB Driver not found!");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Start the command line interface.
        CLI cli = new CLI(db);

    }
}