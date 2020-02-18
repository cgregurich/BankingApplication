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
            
            String[] customerInfo = new String[9];
            
            
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
    
    public void updateAccountNum(Customer c){
        String query = "UPDATE " +TABLE_NAME
                + " SET accountNum = ?"
                + " WHERE firstName = ? AND lastName = ?"
                + " AND phoneNumber = ? AND streetAddress = ?"
                + " AND aptNum = ? AND city = ? AND state = ?"
                + " AND zip = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, c.getAccountNum());
            
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getPhoneNumber());
            ps.setString(5, c.getAddress().getStreetAddress());
            ps.setString(6, c.getAddress().getAptNum());
            ps.setString(7, c.getAddress().getCity());
            ps.setString(8, c.getAddress().getState());
            ps.setString(9, c.getAddress().getZipCode());
            
            ps.executeUpdate();
            
        } catch (SQLException e){
            System.err.println(e);
        }
    }
    
    public void updateCustomer(Customer c){
        String query = "UPDATE " +TABLE_NAME
                + " SET firstName = ?, lastName = ?, phoneNumber = ?, "
                + " streetAddress = ?, aptNum = ?, city = ?, state = ?, zip = ?"
                + " WHERE accountNum = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPhoneNumber());
            
            Address a = c.getAddress();
            
            ps.setString(4, a.getStreetAddress());
            ps.setString(5, a.getAptNum());
            ps.setString(6, a.getCity());
            ps.setString(7, a.getState());
            ps.setString(8, a.getZipCode());
            
            ps.setString(9, c.getAccountNum());
            ps.executeUpdate();
            
        } catch (SQLException e){
            System.err.println(e);
        }
    }
    
}
