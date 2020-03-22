package models;

import java.sql.*;
import java.util.*;

public class UserBookQueryRow {
    private String callnum;
    private String title;
    private ArrayList<String> authors;
    private int availableCopies;

    public UserBookQueryRow() {
        authors = new ArrayList<String>();
    }

    // The return value is true when there is a match. Otherwise, false.
    public boolean selectFromDB(Connection conn, String callnum) throws SQLException {
        this.callnum = callnum;

        // book.
        PreparedStatement bookStmt = conn.prepareStatement("SELECT title FROM book WHERE callnum = ?");
        bookStmt.setString(1, callnum);
        ResultSet bookResSet = bookStmt.executeQuery();
        if (!bookResSet.next()) return false;
        title = bookResSet.getString(1);

        // author.
        PreparedStatement authorStmt = conn.prepareStatement("SELECT aname FROM authorship WHERE callnum = ?");
        authorStmt.setString(1, callnum);
        ResultSet authorResSet = authorStmt.executeQuery();
        while (authorResSet.next()) {
            authors.add(authorResSet.getString(1));
        }

        // available copies.
        PreparedStatement copyStmt = conn.prepareStatement("SELECT COUNT(*) FROM copy WHERE (callnum, copynum) NOT IN "
            + "(SELECT callnum, copynum FROM borrow WHERE callnum = ? AND RET IS NULL) AND callnum = ?");
        copyStmt.setString(1, callnum);
        copyStmt.setString(2, callnum);
        ResultSet rs = copyStmt.executeQuery();
        rs.next();
        availableCopies = rs.getInt(1);

        return true;
    }

    public void printRow() {
        System.out.printf("|" + callnum + "|" + title + "|");
        boolean first = true;
        for (String a : authors) {
            System.out.printf(first ? a : (", " + a));
            first = false;
        }
        System.out.println("|" + availableCopies + "|");
    }

}