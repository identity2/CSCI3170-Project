package models.db;

import java.sql.*;
import java.util.Calendar;

public class BorrowDBModel {
    private String libuid;
    private String callnum;
    private int copynum;
    private Calendar checkout;
    private Calendar ret;

    public BorrowDBModel(String libuid, String callnum, int copynum, Calendar checkout, Calendar ret) {
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
            stmt.setDate(4, new Date(checkout.getTimeInMillis()));

            // Return date could be null.
            if (ret == null) {
                stmt.setNull(5, Types.DATE);
            } else {
                stmt.setDate(5, new Date(ret.getTimeInMillis()));
            }

            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}