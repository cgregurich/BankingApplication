/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author colin
 */
public class AddCustomerWindowController implements Initializable {
    
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField streetAddressTextField;
    @FXML private TextField secondaryAddressTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField stateTextField;
    @FXML private ComboBox stateComboBox;
    @FXML private TextField zipTextField;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadStateChoiceBox();
    }   
    
    @FXML
    private void addCustomerButtonClicked(){
        //since no db isn't set up yet
        Customer customer = createCustomer();
        //TESTING
        System.out.println("New customer added: ");
        System.out.println(customer);
    }
    
    @FXML
    private void backButtonClicked(){
        //TODO
    }
    
    private void loadStateChoiceBox(){
        stateComboBox.setItems(FXCollections.observableArrayList(State.values()));
    }
    
    private String getSelectedState(){
        String state = stateComboBox.getValue().toString();
        return state;
    }
    
    private Customer createCustomer(){
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        Address address = createAddress();
        
        Customer c = new Customer(firstName, lastName, phoneNumber, address);
        
        return c;
    }
    
    private Address createAddress(){
        String address = streetAddressTextField.getText();
        String aptNum = secondaryAddressTextField.getText();
        String city = cityTextField.getText();
        String state = getSelectedState();
        String zipCode = zipTextField.getText();
        
        Address a = new Address();
        a.setAddress(address);
        a.setAptNum(aptNum);
        a.setCity(city);
        a.setState(state);
        a.setZipCode(zipCode);
        
        //TESTING
        System.out.println("printing out apt: ");
        System.out.println(a.getAptNum());
        
        return a;
    }
    
}
