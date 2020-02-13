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
    
    public Customer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(String firstName, String lastName, String phoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.phoneNumber = putHyphensInPhoneNumber();
    }
    
    private String putHyphensInPhoneNumber(){
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
                +"\nPhone number: " +this.phoneNumber;
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
        this.phoneNumber = putHyphensInPhoneNumber();
    }
    
    
    
    
    
    
}
