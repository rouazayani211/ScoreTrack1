package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    private final String URL ="jdbc:mysql://localhost:3306/scoretrack";
    private final String USER ="root";
    private final String PWD ="";
    //3variable pour stocket l'instance
    private static MyDB instance;


   private Connection connection;
   //  1 rendre le constrcuteur privee
   private MyDB(){
        try {
            connection = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("connected to db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//2 cree une methode static pour utuliser le constructeur
    public static MyDB getInstance(){
       if (instance==null) {
           instance = new MyDB();

       }return instance ;

    }

    public Connection getConnection() {
        return connection;
    }
}
