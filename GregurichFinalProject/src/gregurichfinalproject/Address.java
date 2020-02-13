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
public class Address {
    private String streetAddress;
    private String aptNum;
    private boolean isApartment = false;
    private String city;
    private String state;
    private String zipCode;
    
    

    public Address() {
        
    }

    public Address(String address, String city, String state, String zipCode) {
        this.streetAddress = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return streetAddress;
    }

    public void setAddress(String address) {
        this.streetAddress = address;
    }
    
    
    
    public String getAptNum() {
        return aptNum;
    }

    public void setAptNum(String aptNum) {
        this.aptNum = aptNum;
        
        this.isApartment = !this.aptNum.isEmpty();
        
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Override
    public String toString(){
        
        String returnStr = streetAddress;
        if (isApartment){
            returnStr += "#" +aptNum+ "\n";
        }
        returnStr += "\n" +city+ ", " +state+ " " +zipCode;
        
        return returnStr;
    }
    
    
    
    
    
}
