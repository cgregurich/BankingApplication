/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author colin
 */
public class SavingsAccount {
    private List<Customer> ownersList = new ArrayList<>();
    private String accountNum;
    private double balance;
    private double interestRate; //as a decimal: 1.5% = .015
    
    private AccountDAO accountDb = new AccountDAO();

    public SavingsAccount(double interestRate) {
        
        this.accountNum = generateAccountNum();
        this.balance = 0.00;
        this.interestRate = interestRate;
    }
    
    public SavingsAccount(String[] info){
        this.accountNum = info[0];
        this.balance = Double.parseDouble(info[1]);
        this.interestRate = Double.parseDouble(info[2]);
    }

    public List<Customer> getOwnersList() {
        return ownersList;
    }

    public void setOwnersList(List<Customer> ownersList) {
        this.ownersList = ownersList;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public double getBalance(){
        return this.balance;
    }
    
    public String getBalanceFormatted() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(balance);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
    private String generateAccountNum(){
        Random rng = new Random();
        String accountNum = "A";
        boolean isUnique = false;
        
        while (!isUnique){
            while (accountNum.length() < 9){
                accountNum += rng.nextInt(9999);
            }
            isUnique = accountDb.isUniqueAccountNum(accountNum);
        }
        return accountNum;
    }
    
    public void assignAccountNumber(){
        this.accountNum = generateAccountNum();
    }
    
    
    
    
    
    
    
    
}
