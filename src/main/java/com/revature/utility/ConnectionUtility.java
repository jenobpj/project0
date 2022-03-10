package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//create connectionUtility method to connect the database
public class ConnectionUtility {
    public static Connection getConnection() throws SQLException {
        //grab the connection string (url),username,password for the database

        String url=System.getenv("db_url");
        String username=System.getenv("db_user");
        String password= System.getenv("db_password");

        //Register the Postgres driver with the DriverManager
        DriverManager.registerDriver(new Driver());

        //Get the connection object from the DriverManager
        Connection connection =DriverManager.getConnection(url,username,password);

        return connection;

    }

}

