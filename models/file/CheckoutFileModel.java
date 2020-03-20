package models.file;

import java.sql.Connection;

import models.db.BorrowDBModel;

public class CheckoutFileModel implements FileModelInterface{
    private String callNumber;
    private int copyNumber;
    private String userID;
    private String checkOutDate;
    private String returnDate;

    public void parseFromLine(String inputLine) {
        String[] splitted = inputLine.split("\t");
        this.callNumber = splitted[0];
        this.copyNumber = Integer.parseInt(splitted[1]);
        this.userID = splitted[2];
        this.checkOutDate = splitted[3];
        this.returnDate = splitted[4];
    }

    public void saveToDatabase(Connection conn) {
        BorrowDBModel borrow = new BorrowDBModel(userID, callNumber, copyNumber, checkOutDate, returnDate);
        borrow.insertToDatabase(conn);
    }
}