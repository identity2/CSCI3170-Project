package models.db;

import java.sql.*;

public class CategoryDBModel {
    private int cid;
    private int max;
    private int period;

    public CategoryDBModel(int cid, int max, int period) {
        this.cid = cid;
        this.max = max;
        this.period = period;
    }

    public void insertToDatabase(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO category (cid, max, period) VALUES (?, ?, ?)");
            stmt.setInt(1, cid);
            stmt.setInt(2, max);
            stmt.setInt(3, period);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}