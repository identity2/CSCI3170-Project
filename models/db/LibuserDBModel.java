package models.db;

import java.sql.*;

public class LibuserDBModel {
    private String libuid;
    private String name;
    private String address;
    private int cid;

    public LibuserDBModel(String libuid, String name, String address, int cid) {
        this.libuid = libuid;
        this.name = name;
        this.address = address;
        this.cid = cid;
    }

    public void insertToDatabase(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO libuser (libuid, name, address, cid) VALUES (?, ?, ?, ?)");
            stmt.setString(1, libuid);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.setInt(4, cid);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}