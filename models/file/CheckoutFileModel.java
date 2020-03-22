package models.file;

import java.sql.*;
import java.util.Calendar;

import models.*;
import models.db.BorrowDBModel;

public class CheckoutFileModel implements FileModelInterface{
    private String callNumber;
    private int copyNumber;
    private String userID;
    private Calendar checkOutDate;
    private Calendar returnDate;

    public void parseFromLine(String inputLine) {
        String[] splitted = inputLine.split("\t");
        this.callNumber = splitted[0];
        this.copyNumber = Integer.parseInt(splitted[1]);
        this.userID = splitted[2];
        this.checkOutDate = DateConv.strToCal(splitted[3]);

        if (!splitted[4].equals("null")) {
            this.returnDate = DateConv.strToCal(splitted[4]);
        } else {
            this.returnDate = null;
        }
        
    }

    public void saveToDatabase(Connection conn) {
        BorrowDBModel borrow = new BorrowDBModel(userID, callNumber, copyNumber, checkOutDate, returnDate);
        borrow.insertToDatabase(conn);
    }
}