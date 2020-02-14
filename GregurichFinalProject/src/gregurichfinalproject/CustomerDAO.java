/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author colin
 */
public class CustomerDAO {
    private final String SQL_NAME = "CustomersDB";
    private final String TABLE_NAME = "Customers";
    
    
    public Connection getConnection(){
        String dbUrl = "jdbc:sqlite:" +SQL_NAME+ ".sqlite";
        
        try{
            Connection connection = DriverManager.getConnection(dbUrl);
            return connection;
            
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
    public List<Customer> getAll(){
        String query = "SELECT * from " +TABLE_NAME;
        
        List<Customer> customers = new ArrayList<>();
        
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){
            
            String[] customerInfo = new String[8];
            
            
            //populates an array with the the info on the database
            while (rs.next()){
                for (int i = 0; i < customerInfo.length; i++){
                    customerInfo[i] = rs.getString(i+1);
                }
                Customer c = new Customer(customerInfo);
                customers.add(c);
            }
            
            return customers;
            
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
    public boolean add(Customer newCustomer){
        if (customerAlreadyExists(newCustomer)){
            return false;
        }
        
        String query = "INSERT INTO " +TABLE_NAME
                + "(firstName, lastName, phoneNumber, streetAddress, aptNum, city, "
                + "state, zip)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            
            ps.setString(1, newCustomer.getFirstName());
            ps.setString(2, newCustomer.getLastName());
            ps.setString(3, newCustomer.getPhoneNumber());
            ps.setString(4, newCustomer.getAddress().getStreetAddress());
            ps.setString(5, newCustomer.getAddress().getAptNum());
            ps.setString(6, newCustomer.getAddress().getCity());
            ps.setString(7, newCustomer.getAddress().getState());
            ps.setString(8, newCustomer.getAddress().getZipCode());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }

    }
    
    public boolean customerAlreadyExists(Customer newCustomer){
        for (Customer c : this.getAll()){
            if (newCustomer.equalsByValue(c)){
                return true;
            }
        }
        
        return false;
        
    }
    
}
