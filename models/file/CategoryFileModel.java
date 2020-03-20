package models.file;

import java.sql.Connection;

import models.db.CategoryDBModel;

public class CategoryFileModel implements FileModelInterface{
    private int categoryID;
    private int maxBooks;
    private int loanPeriod;

    public void parseFromLine(String inputLine) {
        String[] splitted = inputLine.split("\t");
        this.categoryID = Integer.parseInt(splitted[0]);
        this.maxBooks = Integer.parseInt(splitted[1]);
        this.loanPeriod = Integer.parseInt(splitted[2]);
    }

    public void saveToDatabase(Connection conn) {
        CategoryDBModel category = new CategoryDBModel(categoryID, maxBooks, loanPeriod);
        category.insertToDatabase(conn);
    }
}