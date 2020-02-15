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
public class AccountDAO {
    private final String SQL_NAME = "CustomersDB";
    private final String TABLE_NAME = "Accounts";
    
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
    
    public List<SavingsAccount> getAll(){
        String query = "SELECT * from " +TABLE_NAME;
        
        List<SavingsAccount> accounts = new ArrayList<>();
        
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()){
            
            String[] accountInfo = new String[3];
            
            
            //populates an array with the the info on the database
            while (rs.next()){
                for (int i = 0; i < accountInfo.length; i++){
                    accountInfo[i] = rs.getString(i+1);
                }
                SavingsAccount a = new SavingsAccount(accountInfo);
                accounts.add(a);
            }
            
            return accounts;
            
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
    public boolean add(SavingsAccount acct){
        String query = "INSERT INTO " +TABLE_NAME
                + " (accountNum, balance, interestRate)"
                + " VALUES (?, ?, ?)";
        
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, acct.getAccountNum());
            ps.setDouble(2, acct.getBalance());
            ps.setDouble(3, acct.getInterestRate());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }
    
    
    
    public List<String> getAllAccountNumbers(){
        List<SavingsAccount> accounts = this.getAll();
        List<String> accountNums = new ArrayList<>();
        
        for (SavingsAccount a : accounts){
            accountNums.add(a.getAccountNum());
        }
        
        return accountNums;
    }
    
    public boolean isUniqueAccountNum(String acctNum){
        return !getAllAccountNumbers().contains(acctNum);
    }
    
}
