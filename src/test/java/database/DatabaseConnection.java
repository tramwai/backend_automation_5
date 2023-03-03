package database;
import java.sql.*;
public class DatabaseConnection {

    public static void main(String[] args) throws SQLException {

        /**
        In order to connect to the database, we need URL, username, password and query
        NOTE; Interview question; How do you connect to the database?
         */

        String url = "jdbc:oracle:thin:@techglobal.cup7q3kvh5as.us-east-2.rds.amazonaws.com:1521/TGDEVQA";
        String username = "mihail";
        String password = "$mihail123!";
        String query = "select * from employees";

        // Create a connection to the database with the parameters we stored
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Database connection is successful");


        /**
         * Statement keeps the connection between DB and Automation to send queries
         */
        Statement statement = connection.createStatement();


        // ResultSet is sending the query to the database and get the result
        ResultSet resultSet = statement.executeQuery(query);


        /**
         * ResultSetMetaData gives the information about the table
         * You cannot simply print the column values. We need to call them with iterations
         */
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        System.out.println("Number of the columns: " + resultSetMetaData.getColumnCount());
        System.out.println("Column name: " + resultSetMetaData.getColumnName(1));

        // iterator to get the column info
        while(resultSet.next()){
            System.out.println(resultSet.getString("FIRST_NAME"));
            System.out.println(resultSet.getString("LAST_NAME"));
        }
    }
}


