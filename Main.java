import java.sql.*;
import cli.*;
import models.*;

public class Main {

    public static void main(String[] args) {
        // Connecting to the database.
        Database db = new Database();
        try {
            db.connect();
        } catch (ClassNotFoundException e) {
            System.out.println("[Error] Java MySQL DB Driver not found!");
            System.out.println("Make sure you are connected to CUHK CSE VPN.");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println("Welcome to library inquiry system!");

        // Start the command line interface.
        MainCLI cli = new MainCLI(db);
        cli.startCLI();
    }
}