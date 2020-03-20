package models.db;

import java.sql.*;

public class CopyDBModel {
    private String callnum;
    private int copynum;

    public CopyDBModel(String callnum, int copynum) {
        this.callnum = callnum;
        this.copynum = copynum;
    }

    public void insertToDatabase(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO copy (callnum, copynum) VALUES (?, ?)");
            stmt.setString(1, callnum);
            stmt.setInt(2, copynum);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}