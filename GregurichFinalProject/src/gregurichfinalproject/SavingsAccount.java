/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 *
 * @author colin
 */
public class SavingsAccount {
    private String accountNum;
    private double balance;
    private double interestRate; //as a decimal: 1.5% = .015
    
    //this obj is used to ensure unique account numbers
    private AccountDAO accountDb = new AccountDAO();

    public SavingsAccount(double interestRate) {
        
        this.accountNum = generateAccountNum();
        this.balance = 0.00;
        this.interestRate = interestRate;
    }
    
    /*
    creates a SavingsAccount obj from a String arr parameter
    Used to easily create SavingsAccounts in the AcocuntDAO class
    */
    public SavingsAccount(String[] info){
        this.accountNum = info[0];
        this.balance = Double.parseDouble(info[1]);
        this.interestRate = Double.parseDouble(info[2]);
    }
    
    /*
    Adds the param to the balance
    */
    public void deposit(double amount){
        this.balance += amount;
    }
    
    /*
    Subtracts the param from the balance only if there is enough to avoid
    going negative (no overdrafting at this bank)
    */
    public boolean withdraw(double amount){
        if (this.balance - amount >= 0.00){
            this.balance -= amount;
            return true;
        }
        
        else{
            return false;
        }
    }
    
    

   
    /*
    returns the balance as a currency
    i.e. $X.XX
    */
    public String getBalanceFormatted() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(balance);
    }

   
    /*
    Generates a unique and uniform account number of format:
    AXXXXXXXX where A is A and x is any number
    One hundren million possible account numbers
    */
    private String generateAccountNum(){
        Random rng = new Random();
        String accountNum = "A";
        boolean isUnique = false;
        
        
        /*
        Adds a new random number to the end of the account number until
        the total length reaches 9
        */
        while (!isUnique){
            while (accountNum.length() < 9){
                accountNum += rng.nextInt(10);
            }
            //checks if the generated account number already exists in the DB
            isUnique = accountDb.isUniqueAccountNum(accountNum);
        }
        return accountNum;
    }
    
    /*
    sets this accounts number to the generated account number
    */
    public void assignAccountNumber(){
        this.accountNum = generateAccountNum();
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
    
    public String getInterestRateFormatted(){
        DecimalFormat df = new DecimalFormat("##.##%");
        return df.format(this.interestRate);
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
    
    public double calculateInterestSoFar(Month month){
        
        
        double interestYTD = interestRate * (month.getNumVal() / 12) * balance;
        
        return interestYTD;
    }
    
    public String calculateInterestSoFarFormatted(Month month){
        double interestYTD = calculateInterestSoFar(month);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(interestYTD);
    }
    
    
    
    
    
    
    
    
}
