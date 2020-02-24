/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import gregurichfinalproject.Address;
import gregurichfinalproject.Customer;
import gregurichfinalproject.State;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    @FXML private ComboBox stateComboBox;
    @FXML private TextField zipTextField;
    
   
    @FXML private Label statusLabel;
    
    private List<TextField> textFields = new ArrayList<>();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusLabel.setText("");
        
        textFields.add(firstNameTextField);
        textFields.add(lastNameTextField);
        textFields.add(phoneNumberTextField);
        textFields.add(streetAddressTextField);
        textFields.add(secondaryAddressTextField);
        textFields.add(cityTextField);
        textFields.add(zipTextField);
        
        loadStateComboBox();
    }   
    
    
    @FXML
    private void addCustomerButtonClicked(){
        if (isMissingFields()){
            return;
        }
        
        if (!isPhoneNumberValid()){
            return;
        }
        
        if (!isStateSelected()){
            return;
        }
        
        if (!isZipCodeValid()){
            return;
        }
        
        CustomerDAO customerDb = new CustomerDAO();
        Customer newCustomer = createCustomer();
        boolean isAdded = customerDb.add(newCustomer);
        if (isAdded){
            statusLabel.setText("Customer added!");
        }
        
        else{
            statusLabel.setText("Customer already exists!");
        }
    }
    
    /*
    returns true if any of the window's textfields have no text
    displays an error message is any are missing
    */
    private boolean isMissingFields(){
        for (TextField field : this.textFields){
            if (field != secondaryAddressTextField && field.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Required field missing");
                alert.setContentText(field.getPromptText()+ " is a required field");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }
    
    /*
    returns true if user has selected a state
    displays an error message if no state is selected
    */
    private boolean isStateSelected(){
        if (stateComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No state selected");
            alert.setContentText("Please select a state");
            alert.showAndWait();
            return false;
        }
        return true;
        
    }
    
    /*
    returns true if the phone number text field is valid
    validity is checked using helper methods
    */
    private boolean isPhoneNumberValid(){        
        return isPhoneNumberTenDigits() && isPhoneNumberOnlyNumbers();
    }
    
    /*
    returns true if the phone number text field contains only numbers
    if not then displays an error message
    */
    private boolean isPhoneNumberOnlyNumbers(){
        try{
            long phoneNumberInt = Long.parseLong(phoneNumberTextField.getText());
            return true;
            
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid phone number");
            alert.setContentText("Phone number must only contain numbers");
            alert.showAndWait();
            return false; 
        }
    }
    
    /*
    returns true if the phone number text field is 10 digits
    if not displays an error message
    */
    private boolean isPhoneNumberTenDigits(){
        if (phoneNumberTextField.getText().length() == 10){
            return true;
        }
        
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid phone number");
            alert.setContentText("Phone number must be 10 digits");
            alert.showAndWait();
            return false;
        }
    }
    
    /*
    returns true if the zip code text field is a valid zip code
    uses helper methods to check validity
    */
    private boolean isZipCodeValid(){
        return isZipCodeOnlyNumbers() && isZipCodeFiveDigits();
    }
    
    /*
    returns true if the zip code text field contains only numbers 
    if not displays an error message
    */
    private boolean isZipCodeOnlyNumbers(){
        try{
            int zipCodeInt = Integer.parseInt(zipTextField.getText());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid zip code");
            alert.setContentText("Zip code must only contain numbers");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    /*
    returns true if zip code text field is five digits
    if not displays an error message
    */
    private boolean isZipCodeFiveDigits(){
        
        if (zipTextField.getText().length() == 5){
            return true;
        }
        
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid zip code");
            alert.setContentText("Zip code must be 5 digits");
            alert.showAndWait();
            return false;
        }
    }
    
    /*
    returns to the main menu
    */
    @FXML
    private void backButtonClicked(ActionEvent event) throws Exception{
         Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    /*
    clears all editable nodes 
    */
    @FXML
    private void clearButtonClicked(){
        statusLabel.setText("");
        
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneNumberTextField.clear();
        streetAddressTextField.clear();
        secondaryAddressTextField.clear();
        cityTextField.clear();
        stateComboBox.valueProperty().set(null); 
        zipTextField.clear();
        
    }
    
    
    @FXML
    private void clearStatusLabel(){
        statusLabel.setText("");
    }
    
    /*
    fills the combo box node with all values from enum State
    */
    private void loadStateComboBox(){
        stateComboBox.setItems(FXCollections.observableArrayList(State.values()));
    }
    
    /*
    returns the selected state in the combo box
    */
    private String getSelectedState(){
        String state = stateComboBox.getValue().toString();
        return state;
    }
    
    /*
    creates a Customer object from the current text in the text fields
    */
    private Customer createCustomer(){
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        Address address = createAddress();
        
        Customer c = new Customer(firstName, lastName, phoneNumber, address);
        
        return c;
    }
    
    /*
    creates an Address object from the current text in the text fields
    */
    private Address createAddress(){
        String address = streetAddressTextField.getText();
        String aptNum = secondaryAddressTextField.getText();
        String city = cityTextField.getText();
        String state = getSelectedState();
        String zipCode = zipTextField.getText();
        
        Address a = new Address();
        a.setStreetAddress(address);
        a.setAptNum(aptNum);
        a.setCity(city);
        a.setState(state);
        a.setZipCode(zipCode);
        
        return a;
    }
    
}
