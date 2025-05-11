package RailwayManagementSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    
    // Database connection parameters
    private String user = "root";         // MySQL username
    private String pass = "";             // MySQL password (empty by default in XAMPP)
    private String url = "jdbc:mysql://localhost/railwaymanagementsystem";  // Database URL
    private Statement statement;          // Statement for executing SQL queries
    private Connection connection;        // Connection object to manage the database connection

    // Constructor that establishes the database connection
    public Database() throws SQLException {
        try {
            // Load and register MySQL JDBC driver (Optional in modern setups but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establishing connection to the MySQL database
            connection = DriverManager.getConnection(url, user, pass);
            
            // Creating the statement object for executing queries
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                     ResultSet.CONCUR_READ_ONLY);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
            throw e; // Re-throwing the exception to propagate error
        }
    }

    // Getter method to access the statement
    public Statement getStatement() {
        return statement;
    }

    // Close the database connection and statement (important for resource management)
    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
