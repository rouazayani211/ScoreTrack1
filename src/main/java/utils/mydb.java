package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mydb {
    private final String URL="jdbc:mysql://localhost:3306/scoretrack";
    private final String USER="root";
    private final String PWD="";
    private Connection connection;
    private static mydb instance;
    private mydb()
    {
        try {
            connection = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("connected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static mydb getInstance(){
        if(instance==null){
            instance = new mydb();
        } return instance;
    }
    public Connection getConnection(){return connection;}
}
