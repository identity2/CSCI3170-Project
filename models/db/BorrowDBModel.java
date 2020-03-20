package models.db;

import java.sql.*;

public class BorrowDBModel {
    private String libuid;
    private String callnum;
    private int copynum;
    private String checkout;
    private String ret;

    public BorrowDBModel(String libuid, String callnum, int copynum, String checkout, String ret) {
        this.libuid = libuid;
        this.callnum = callnum;
        this.copynum = copynum;
        this.checkout = checkout;
        this.ret = ret;
    }

    public void insertToDatabase(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO borrow (libuid, callnum, copynum, checkout, ret) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, libuid);
            stmt.setString(2, callnum);
            stmt.setInt(3, copynum);
            stmt.setString(4, checkout);

            // Return date could be null.
            if (ret == "null") {
                stmt.setNull(5, Types.CHAR);
            } else {
                stmt.setString(5, ret);
            }

            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}