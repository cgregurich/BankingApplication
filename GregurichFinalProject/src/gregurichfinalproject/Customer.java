/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

/**
 *
 * @author colin
 */
public class Customer {
    /*
    each customer will have the following information associated with them: 
    first name
    last name
    address
    phone number
    */
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;
    
    
    
    
    private String accountNum;
    
    private AccountDAO accountDb = new AccountDAO();
    
    public Customer(){
        
    }
    
    
    /*
    creates a Customer obj from a String arr parameter
    Used to easily create Customers in the CustomerDAO class
    */
    public Customer(String[] customerInfo){ //array of 8 elements
        this.firstName = customerInfo[0];
        this.lastName = customerInfo[1];
        this.phoneNumber = customerInfo[2];
        
        //creates address object
        String street = customerInfo[3];
        String aptNum = customerInfo[4];
        String city = customerInfo[5];
        String state = customerInfo[6];
        String zip = customerInfo[7];
        Address a = new Address(street, aptNum, city, state, zip);
        
        this.accountNum = customerInfo[8];
        
        this.address = a;
        
    }
    
    
    public Customer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(String firstName, String lastName, String phoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.phoneNumber = hyphenatePhoneNumber();
    }
    
    /*
    Formats var phoneNumber to have hyphens in the typical format
    i.e. XXX-XXX-XXXX
    */
    private String hyphenatePhoneNumber(){
        if (this.phoneNumber == null){
            return null;
        }
        
        String unformatted = this.phoneNumber;
        
        if (unformatted.length() == 12){
            return unformatted;
        }
        
        String formatted = "";
        formatted += unformatted.substring(0, 3)+ "-";
        formatted += unformatted.substring(3, 6)+ "-";
        formatted += unformatted.substring(6, 10)+ "";
        
        return formatted;
    }
    
    

    
    @Override
    public String toString(){
        return this.firstName+ " " +this.lastName+ "\nAddress: " +this.address
                +"\nPhone number: " +this.phoneNumber +"\nAccount #: " +this.accountNum;
    }
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.phoneNumber = hyphenatePhoneNumber();
    }
    
    
    /*
    Returns a boolean based on the values of two customer objects
    Checks values of: first name, last name, phone, and address
    Not account number because no two Customers will have the same acct num
    */
    public boolean equalsByValue(Customer c2){
        if (!this.firstName.equals(c2.firstName)){
            return false;
        }
        
        if (!this.lastName.equals(c2.lastName)){
            return false;
        }
        
        if (!this.address.equalsByValue(c2.getAddress())){
            return false;
        }
        
        if (!this.phoneNumber.equals(c2.getPhoneNumber())){
            return false;
        }
        
        return true;
    }
    
    
    public String getAccountNum(){
        return this.accountNum;
    }
    
    public void setAccountNum(String accountNum){
        this.accountNum = accountNum;
    }
    
    /*
    Creates a SavingsAccount obj and initializes it to the starting interest rate
    of 1%. Then sets this account number to the number of the created SavingsAccount obj
    */
    public SavingsAccount openAccount(){
        SavingsAccount newAcct = new SavingsAccount(0.01);
        setAccountNum(newAcct.getAccountNum());
        return newAcct;
    }
    
    
    
    
    
    
    
    
}
