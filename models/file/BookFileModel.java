package models.file;

import java.sql.Connection;

import models.db.*;

public class BookFileModel implements FileModelInterface {
    private String callNumber;
    private int numOfCopies;
    private String title;
    private String[] authors;
    private String dateOfPublication;

    public void parseFromLine(String inputLine) {
        String[] splitted = inputLine.split("\t");
        this.callNumber = splitted[0];
        this.numOfCopies = Integer.parseInt(splitted[1]);
        this.title = splitted[2];
        this.authors = splitted[3].split(",");
        this.dateOfPublication = splitted[4];
    }

    public void saveToDatabase(Connection conn) {
        // Book
        BookDBModel book = new BookDBModel(callNumber, title, dateOfPublication);
        book.insertToDatabase(conn);

        // Copy
        for (int copynum = 1; copynum <= numOfCopies; copynum++) {
            CopyDBModel copy = new CopyDBModel(callNumber, copynum);
            copy.insertToDatabase(conn);
        }

        // Author
        for (String author : authors) {
            AuthorshipDBModel authorship = new AuthorshipDBModel(author, callNumber);
            authorship.insertToDatabase(conn);
        }
    }
}