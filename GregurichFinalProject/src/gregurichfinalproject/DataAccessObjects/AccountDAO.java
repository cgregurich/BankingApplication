/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject.DataAccessObjects;

import gregurichfinalproject.SavingsAccount;
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
    
    
    /*
    returns a list containing all savings accounts in the database
    */
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
    
    /*
    adds the param savings account to the database
    */
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
    
    /*
    changes the balance of the account in the DB matching the param to the param's
    balance
    */
    public boolean updateBalance(SavingsAccount a){
        String query = "UPDATE " +TABLE_NAME
                + " SET balance = ?"
                + " WHERE accountNum = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            
            ps.setDouble(1, a.getBalance());
            ps.setString(2, a.getAccountNum());
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }
    
    /*
    deletes all accounts with the param account's account number
    (will only be one account because account nums are unique)
    */
    public boolean deleteAccount(SavingsAccount account){
         String query = "DELETE FROM " +TABLE_NAME+ " WHERE accountNum = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, account.getAccountNum());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }
    
    
    /*
    returns a list of all the savings accounts' account numbers from the DB
    */
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
    
    public SavingsAccount getByAcctNum(String acctNum){
        for (SavingsAccount a : this.getAll()){
            if (a.getAccountNum().equals(acctNum)){
                return a;
            }
        }
        return null;
    }
    
}
