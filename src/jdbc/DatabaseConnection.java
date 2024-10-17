package jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/JobBoard", "root", "dieter113");
        }catch(SQLException e){
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
}

