package models;

import java.sql.*;
import java.io.*;
import models.file.*;

public class Database {
    final String dbAddr = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db27";
    final String dbUsername = "Group27";
    final String dbPassword = "password";

    private Connection conn = null;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(dbAddr, dbUsername, dbPassword);
    }

    public void createAllTables() throws SQLException {
        PreparedStatement[] stmts = {
            conn.prepareStatement("CREATE TABLE category (cid INTEGER NOT NULL, max INTEGER NOT NULL, period INTEGER NOT NULL, PRIMARY KEY (cid))"),
            conn.prepareStatement("CREATE TABLE libuser (libuid CHAR(10) NOT NULL, name VARCHAR(25) NOT NULL, address VARCHAR(100) NOT NULL, cid INTEGER NOT NULL, PRIMARY KEY (libuid))"),
            conn.prepareStatement("CREATE TABLE book (callnum CHAR(8) NOT NULL, title VARCHAR(30) NOT NULL, publish CHAR(10) NOT NULL, PRIMARY KEY (callnum))"),
            conn.prepareStatement("CREATE TABLE copy (callnum CHAR(8) NOT NULL, copynum INTEGER NOT NULL, PRIMARY KEY (callnum, copynum))"),
            conn.prepareStatement("CREATE TABLE borrow (libuid CHAR(10) NOT NULL, callnum CHAR(8) NOT NULL, copynum INTEGER NOT NULL, checkout CHAR(10) NOT NULL, ret CHAR(10), PRIMARY KEY (libuid, callnum, copynum, checkout))"),
            conn.prepareStatement("CREATE TABLE authorship (aname VARCHAR(25) NOT NULL, callnum CHAR(8) NOT NULL, PRIMARY KEY (aname, callnum))")
        };

        for (int i = 0; i < stmts.length; i++) {
            stmts[i].execute();
        }
    }

    public void deleteAllTables() throws SQLException {
        PreparedStatement[] stmts = {
            conn.prepareStatement("DROP TABLE category"),
            conn.prepareStatement("DROP TABLE libuser"),
            conn.prepareStatement("DROP TABLE book"),
            conn.prepareStatement("DROP TABLE copy"),
            conn.prepareStatement("DROP TABLE borrow"),
            conn.prepareStatement("DROP TABLE authorship")
        };

        for (int i = 0; i < stmts.length; i++) {
            stmts[i].execute();
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
}