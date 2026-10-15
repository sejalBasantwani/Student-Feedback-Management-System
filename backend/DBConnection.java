    
package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
    
String url = "jdbc:mysql://localhost:3306/student_feedback_db";
String username = System.getenv("DB_USERNAME");
String password = System.getenv("DB_PASSWORD");


    return DriverManager.getConnection(url, username, password);
}
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/student_feedback_db";
        String username = "feedback_app";
        String password = "FeedbackApp@2026";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection con = DriverManager.getConnection(
                url, username, password
            );

            System.out.println("Connected to MySQL successfully!");

            con.close();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }

    }
}


