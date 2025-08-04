package RailwayManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String user = "root"; // Change to 'root' if that's your username
    private String pass = "Msdhoni@7";
    private String url = "jdbc:mysql://localhost:3306/railwaymanagementsystem"; // Updated DB URL
    private Statement statement;

    public Database() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
    }

    public Statement getStatement() {
        return statement;
    }
}
