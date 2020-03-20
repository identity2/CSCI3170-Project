package models.file;

import java.sql.Connection;

public interface FileModelInterface {
    public void saveToDatabase(Connection conn);
    public void parseFromLine(String inputLine);
}