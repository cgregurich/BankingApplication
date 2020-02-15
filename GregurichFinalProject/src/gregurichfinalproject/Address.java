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
    
    public Address(String address, String aptNum, String city, String state, String zipCode) {
        this.streetAddress = address;
        this.aptNum = aptNum;
        this.isApartment = !this.aptNum.isEmpty();
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    
    public boolean equalsByValue(Address a2){
        if (!this.streetAddress.equals(a2.getStreetAddress())){
            return false;
        }
        
        if (isApartment && !this.aptNum.equals(a2.getAptNum())){
            return false;
        }
        
        if (!this.city.equals(a2.getCity())){
            return false;
        }
        
        if (!this.state.equals(a2.getState())){
            return false;
        }
        
        if (!this.zipCode.equals(a2.getZipCode())){
            return false;
        }
        return true;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String address) {
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
    
    public String getCityStateZip(){
        return city+ ", " +state+ " " +zipCode;
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
