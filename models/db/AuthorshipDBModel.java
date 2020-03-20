package models.db;

import java.sql.*;

public class AuthorshipDBModel {
    private String aname;
    private String callnum;

    public AuthorshipDBModel(String aname, String callnum) {
        this.aname = aname;
        this.callnum = callnum;
    }

    public void insertToDatabase(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO authorship (aname, callnum) VALUES (?, ?)");
            stmt.setString(1, aname);
            stmt.setString(2, callnum);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}