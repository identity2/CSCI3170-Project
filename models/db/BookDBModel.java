package models.db;

import java.sql.*;

public class BookDBModel {
    private String callnum;
    private String title;
    private String publish;

    public BookDBModel(String callnum, String title, String publish) {
        this.callnum = callnum;
        this.title = title;
        this.publish = publish;
    }

    public void insertToDatabase(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (callnum, title, publish) VALUES (?, ?, ?)");
            stmt.setString(1, callnum);
            stmt.setString(2, title);
            stmt.setString(3, publish);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}