package models.db;

import java.sql.*;
import java.util.Calendar;

public class BookDBModel {
    private String callnum;
    private String title;
    private Calendar publish;

    public BookDBModel(String callnum, String title, Calendar publish) {
        this.callnum = callnum;
        this.title = title;
        this.publish = publish;
    }

    public void insertToDatabase(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (callnum, title, publish) VALUES (?, ?, ?)");
            stmt.setString(1, callnum);
            stmt.setString(2, title);
            stmt.setDate(3, new Date(publish.getTimeInMillis()));
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}