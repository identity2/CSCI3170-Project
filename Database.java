import java.sql.*;

public class Database {
    final String dbAddr = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db27";
    final String dbUsername = "Group27";
    final String dbPassword = "password";

    private Connection con = null;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.con = DriverManager.getConnection(dbAddr, dbUsername, dbPassword);
    }
}