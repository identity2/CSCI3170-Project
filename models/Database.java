package models;

import java.sql.*;
import java.io.*;
import java.util.*;
import models.file.*;

public class Database {
    final String dbAddr = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db27";
    final String dbUsername = "Group27";
    final String dbPassword = "password";

    final String[] tableNames = {"category", "libuser", "book", "copy", "borrow", "authorship"};

    private Connection conn = null;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(dbAddr, dbUsername, dbPassword);
    }

    // ====== ADMIN OPERATIONS =======

    public void createAllTables() throws SQLException {
        PreparedStatement[] stmts = {
            conn.prepareStatement("CREATE TABLE category (cid INTEGER NOT NULL, max INTEGER NOT NULL, period INTEGER NOT NULL, PRIMARY KEY (cid))"),
            conn.prepareStatement("CREATE TABLE libuser (libuid CHAR(10) NOT NULL, name VARCHAR(25) NOT NULL, address VARCHAR(100) NOT NULL, cid INTEGER NOT NULL, PRIMARY KEY (libuid))"),
            conn.prepareStatement("CREATE TABLE book (callnum CHAR(8) NOT NULL, title VARCHAR(30) NOT NULL, publish DATE NOT NULL, PRIMARY KEY (callnum))"),
            conn.prepareStatement("CREATE TABLE copy (callnum CHAR(8) NOT NULL, copynum INTEGER NOT NULL, PRIMARY KEY (callnum, copynum))"),
            conn.prepareStatement("CREATE TABLE borrow (libuid CHAR(10) NOT NULL, callnum CHAR(8) NOT NULL, copynum INTEGER NOT NULL, checkout DATE NOT NULL, ret DATE, PRIMARY KEY (libuid, callnum, copynum, checkout))"),
            conn.prepareStatement("CREATE TABLE authorship (aname VARCHAR(25) NOT NULL, callnum CHAR(8) NOT NULL, PRIMARY KEY (aname, callnum))")
        };

        for (int i = 0; i < stmts.length; i++) {
            stmts[i].execute();
        }
    }

    public void deleteAllTables() throws SQLException {
        for (int i = 0; i < tableNames.length; i++) {
            PreparedStatement stmt = conn.prepareStatement("DROP TABLE " + tableNames[i]);
            stmt.execute();
        }
    }

    public void loadDataFromFiles(String folderPath) {
        readToModelAndSaveToDB(folderPath + "/category.txt", CategoryFileModel.class);
        readToModelAndSaveToDB(folderPath + "/user.txt", UserFileModel.class);
        readToModelAndSaveToDB(folderPath + "/book.txt", BookFileModel.class);
        readToModelAndSaveToDB(folderPath + "/check_out.txt", CheckoutFileModel.class);
    }

    private void readToModelAndSaveToDB(String filePath, Class<?> type) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                FileModelInterface model = (FileModelInterface) type.newInstance();
                model.parseFromLine(line);
                model.saveToDatabase(conn);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("[Error] " + e);
        }
    }

    public void countAndPrintAllRecordsInTables() {
        for (int i = 0; i < tableNames.length; i++) {
            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM " + tableNames[i]);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);
                System.out.println(tableNames[i] + ": " + count);
            } catch (SQLException e) {
                System.out.println("[Error] Cannot read the tables. (Perhaps the tables are not initialized yet.)");
            }
        }
        System.out.println();
    }

    // ====== LIBRARY USER OPERATIONS ======

    private void printUserSearchResultTitle() {
        System.out.println("|Call Num|Title|Author|Available No. of Copy|");
    }

    public void printSearchByCallnum(String callnum) {
        UserBookQueryRow row = new UserBookQueryRow();
        try { 
            if (!row.selectFromDB(conn, callnum)) {
                System.out.println("*** No Match ***");
                return;
            }

            printUserSearchResultTitle();
            row.printRow();
        } catch (SQLException e) {
            System.out.println("[Error] Failed to perform query.");
        }
    }

    public void printSearchByTitle(String keyword) {
        ArrayList<String> callnums = new ArrayList<String>();
        
        // Select the callnums of the results.
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT callnum FROM book WHERE LOCATE(?, title) > 0 ORDER BY callnum ASC");
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                callnums.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to perform query.");
        }

        if (callnums.isEmpty()) {
            System.out.println("*** No Match ***");
            return;
        }

        // Select the book information of the callnums.
        try {
            printUserSearchResultTitle();
            for (String callnum : callnums) {
                UserBookQueryRow row = new UserBookQueryRow();
                if (!row.selectFromDB(conn, callnum)) {
                    throw new SQLException();
                }
                row.printRow();
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to perform query.");
        }

    }

    public void printSearchByAuthor(String keyword) {
        ArrayList<String> callnums = new ArrayList<String>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT(callnum) FROM authorship WHERE LOCATE(?, aname) > 0 ORDER BY callnum ASC");
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                callnums.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to perform query.");
        }

        if (callnums.isEmpty()) {
            System.out.println("*** No Match ***");
            return;
        }

        try {
            printUserSearchResultTitle();
            for (String callnum : callnums) {
                UserBookQueryRow row = new UserBookQueryRow();
                if (!row.selectFromDB(conn, callnum)) {
                    throw new SQLException();
                }
                row.printRow();
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to perform query.");
        }

    }

    public void printUserRecord(String userID) {
        System.out.println("Loan Record:");
        System.out.println("|CallNum|CopyNum|Title|Author|Check-out|Returned?|");

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT callnum, copynum, checkout, ret FROM borrow WHERE libuid = ? ORDER BY checkout DESC");
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String callnum = rs.getString(1);
                int copynum = rs.getInt(2);
                String title;
                ArrayList<String> authors = new ArrayList<String>();
                Calendar checkoutDate = Calendar.getInstance();
                checkoutDate.setTime(rs.getDate(3));
                boolean hasReturned = rs.getDate(4) != null;

                // Title
                stmt = conn.prepareStatement("SELECT title FROM book WHERE callnum = ?");
                stmt.setString(1, callnum);
                ResultSet titleRS = stmt.executeQuery();
                titleRS.next();
                title = titleRS.getString(1);

                // Authors
                stmt = conn.prepareStatement("SELECT aname FROM authorship WHERE callnum = ?");
                stmt.setString(1, callnum);
                ResultSet authorRS = stmt.executeQuery();
                while (authorRS.next()) {
                    authors.add(authorRS.getString(1));
                }

                // Print result.
                System.out.printf("|" + callnum + "|" + copynum + "|" + title + "|");
                boolean first = true;
                for (String author : authors) {
                    System.out.printf(first ? author : ", " + author);
                    first = false;
                }
                System.out.printf("|" + DateConv.calToStr(checkoutDate) + "|");
                System.out.println(hasReturned ? "Yes|" : "No|");
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to perform query.");
        }
    }
}