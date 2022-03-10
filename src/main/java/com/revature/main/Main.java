package com.revature.main;

import com.revature.dao.ClientsDao;
import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.SQLException;


//Main method
public class Main {

    public static void main(String[] args){
        ClientsDao clientsDao= new ClientsDao();
        try {
          Client s=new Client(4,"james","doe");
          System.out.println(clientsDao.updateClient(s));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
