package models.file;

import java.sql.Connection;

import models.db.LibuserDBModel;

public class UserFileModel implements FileModelInterface{
    private String userID;
    private String name;
    private String address;
    private int categoryID;

    public void parseFromLine(String inputLine) {
        String[] splitted = inputLine.split("\t");
        this.userID = splitted[0];
        this.name = splitted[1];
        this.address = splitted[2];
        this.categoryID = Integer.parseInt(splitted[3]);
    }

    public void saveToDatabase(Connection conn) {
        LibuserDBModel user = new LibuserDBModel(userID, name, address, categoryID);
        user.insertToDatabase(conn);
    }
}