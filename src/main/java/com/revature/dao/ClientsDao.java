package com.revature.dao;


import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Create a Dao (Data access object) class for a particular "entity"
public class ClientsDao {

    //Create the method for the CRUD operations

    //For adding the clients
    //The id is automatically generated so retrieve an id and return that with the student Object
    public Client addClient(Client client) throws  SQLException{
        try(Connection con=ConnectionUtility.getConnection()){
            String sql="INSERT INTO clients(first_name,last_name) VALUES(?,?)";

            PreparedStatement pstmt=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//Return the key when SQL
            //Query is executed

            pstmt.setString(1,client.getFirst_name());
            pstmt.setString(2,client.getLast_name());
            pstmt.executeUpdate();

            ResultSet rs= pstmt.getGeneratedKeys();
            rs.next();
            int generateId=rs.getInt(1);//1st column of the ResultSet

            return new Client(generateId,client.getFirst_name(),client.getLast_name());
        }

    }

    //For getting Client By ID
    public Client getClientByID(int id) throws SQLException {
        //Call the getConnection method from ConnectionUtility
        try(Connection con= ConnectionUtility.getConnection()){
            String sql="SELECT * FROM clients WHERE id =?";

            //Create a preparedStatement object using the connection object which will help for SQL INJUNCTION
            PreparedStatement pstmt=con.prepareStatement(sql);

            //set the parameters
            pstmt.setInt(1,id);

            //Execute the query and retrieve  a ResultSet object
            ResultSet rs =pstmt.executeQuery();//ExecuteQuery used in SELECT

            //it retrieves only one record as unique id and iterate over records using ResultSet's next() method
            if(rs.next()){
                //Grab the information from the record;
                String firstName=rs.getString("first_name");
                String lastName=rs.getString("last_name");

                return new Client(id,firstName,lastName);
            }
        }
        return null;
    }


    //For getting allClients
    public List<Client> getAllClients() throws  SQLException{
        //Creating an array to store all Clients
        List<Client> clients= new ArrayList<>();


        //Call the getConnection method from ConnectionUtility
        try(Connection con= ConnectionUtility.getConnection()){
            String sql="SELECT * FROM clients";

            //Create a preparedStatement object using the connection object which will help for SQL INJUNCTION
            PreparedStatement pstmt=con.prepareStatement(sql);

            //Execute the query and retrieve  a ResultSet object
            ResultSet rs =pstmt.executeQuery();//ExecuteQuery with SELECT

            //it retrieves only one record as unique id and iterate over records using ResultSet's next() method
            while(rs.next()){
                //Grab the information from the record
                int id=rs.getInt("id");
                String firstName=rs.getString("first_name");
                String lastName=rs.getString("last_name");

                //Store all client into client array
                clients.add(new Client(id,firstName,lastName));
            }
            return  clients;
        }
    }


    //For updating ClientInfo
    public Client updateClient(Client client) throws SQLException {
        try(Connection con=ConnectionUtility.getConnection()){
            String sql= "UPDATE clients " +
                    "SET first_name = ?,"+
                    "last_name = ?" +
                    "WHERE id = ?";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1,client.getFirst_name());
            pstmt.setString(2,client.getLast_name());
            pstmt.setInt(3,client.getId());

            pstmt.executeUpdate();
        }
        return client;
    }

    //For deleting
    //True if a record was deleted,false if a record was not deleted
    public boolean deleteClientById(int id) throws  SQLException{
        //Call the getConnection method from ConnectionUtility
        try(Connection con= ConnectionUtility.getConnection()){
            String sql="DELETE  FROM clients WHERE id =?";

            //Create a preparedStatement object using the connection object which will help for SQL INJUNCTION
            PreparedStatement pstmt=con.prepareStatement(sql);

            pstmt.setInt(1,id);


            //Execute the query and it returns an int
            int numberOfRecordsDeleted=pstmt.executeUpdate();//ExecuteUpdate() is used with DELETE,UPDATE,INSERT

            if(numberOfRecordsDeleted == 1){//checks numberOfRecordsDeleted
                return  true;
            }
        }
        return  false;
    }
}
