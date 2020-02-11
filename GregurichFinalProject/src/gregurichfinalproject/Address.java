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
    private String houseNumber;
    private String streetName;
    private boolean isApartment = false;
    private String aptName;
    private String cityName;
    private String state;
    private String zipCode;

    

    

    
    
    

    public Address() {
        
    }

    public Address(String houseNumber, String streetName, String cityName, String state, String zipCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.cityName = cityName;
        this.state = state;
        this.zipCode = zipCode;
    }
    
    

    
    
    
    
    
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
        this.isApartment = true;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
    
    
    
    
    
}
