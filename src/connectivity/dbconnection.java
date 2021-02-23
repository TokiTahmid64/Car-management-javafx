package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
    Connection conn;
    public Connection getConnection()
    {
        String dbName="carDataBase";
        String userName="null";
        String password="";
        Connection connection = null  ;

        try {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:src\\connectivity\\carDataBase.db");
            System.out.println("connected");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;

    }


}
