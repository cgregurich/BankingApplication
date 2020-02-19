/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author colin
 */
public class UpdateInfoWindowController implements Initializable {
    
    private Customer initialCustomer; //customer that was passed to the window
    
    private Customer currentCustomer; //customer that was last saved to the database
    
    
    @FXML
    private Label statusLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField streetAddressTextField;
    @FXML
    private TextField secondaryAddressTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private ComboBox stateComboBox;
    @FXML
    private TextField zipTextField;
    
    private List<TextField> fieldsList;
    
    
    
    
    
    public void initData(Customer customer){
        
        //sets class var to customer object passed when the window was opened
        this.initialCustomer = customer;
        
        //also sets currentCustomer to this parameter
        this.currentCustomer = initialCustomer;
        
        //calls method to fill the text fields with the param's info
        populateWindow();
        populateFieldsList();
        
        
        
    }
    
    
    /*
    sets all the relevant nodes to the customer's information
    Fields are editable so user can edit the information
    */
    private void populateWindow(){
        
        statusLabel.setText("");
        firstNameTextField.setText(initialCustomer.getFirstName());
        lastNameTextField.setText(initialCustomer.getLastName());
        phoneNumberTextField.setText(dehyphenatePhoneNumber(initialCustomer.getPhoneNumber()));
        streetAddressTextField.setText(initialCustomer.getAddress().getStreetAddress());
        secondaryAddressTextField.setText(initialCustomer.getAddress().getAptNum());
        cityTextField.setText(initialCustomer.getAddress().getCity());
        loadStateComboBox();
        stateComboBox.setValue(initialCustomer.getAddress().getState());
        zipTextField.setText(initialCustomer.getAddress().getZipCode());
    }
    
    private void populateFieldsList(){
        fieldsList = new ArrayList<>();
        fieldsList.add(firstNameTextField);
        fieldsList.add(lastNameTextField);
        fieldsList.add(phoneNumberTextField);
        fieldsList.add(streetAddressTextField);
        fieldsList.add(secondaryAddressTextField);
        fieldsList.add(cityTextField);
        fieldsList.add(zipTextField);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    /*
    loads the combo box with all states so user can select one instead
    of having to enter it; makes entry validation easier
    */
    private void loadStateComboBox(){
        stateComboBox.setItems(FXCollections.observableArrayList(State.values()));
    }
    
    
    /*
    when user clicks save, if the customer could be updated, notifies user that
    the changes were saved and sets currentCustomer to a Customer object
    created from the current information in the nodes
    
    if it didn't work, then it tells the user that something went wrong
    */
    @FXML
    private void saveButtonClicked(){
        if (!isInfoValid()){
            return;
        }
        
        if (!isDifferentCustomerInfo()){
            statusLabel.setText("No changes to save");
            return;
        }
        
        if (updateCustomerDb()){
            statusLabel.setText("Changes saved");
            currentCustomer = createUpdatedCustomer();
        }
        else{
            statusLabel.setText("Something went wrong");
        }
        
    }
    
    private boolean isInfoValid(){
        if (isMissingFields()){
            return false;
        }
        
        if (!isPhoneNumberValid()){
            return false;
        }
        
        if (!isStateSelected()){
            return false;
        }
        
        if (!isZipCodeValid()){
            return false;
        }
        
        else{
            return true;
        }
    }
    
    private boolean isMissingFields(){
        for (TextField field : this.fieldsList){
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
    
    private boolean isPhoneNumberValid(){
        return isPhoneNumberTenDigits() && isPhoneNumberOnlyNumbers();
    }
    
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
    
    private boolean isZipCodeValid(){
        return isZipCodeOnlyNumbers() && isZipCodeFiveDigits();
    }
    
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
    
    
    private void goBackToViewCustomers(ActionEvent event) throws Exception{
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ViewCustomersWindow.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        ViewCustomersWindowController controller = loader.getController();
        Customer c = currentCustomer;
        controller.initData(c);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    
    /*
    As long as the current nodes' info is not different from the saved information,
    the window will change to ViewCustomersWindow
    
    If the info is different, then it tells the user to either save or discard
    the changes
    */
    @FXML
    private void backButtonClicked(ActionEvent event) throws Exception{
        if (isDifferentCustomerInfo()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please save or discard changes");
            alert.showAndWait();  
            return;
        }
        
        goBackToViewCustomers(event);
    }
    
    
    /*
    discard simply goes back to ViewCustomersWindow with no validation of saved
    changes
    */
    @FXML
    private void discardButtonClicked(ActionEvent event) throws Exception{
        goBackToViewCustomers(event);
    }
    
    /*
    returns a Customer object populated by the current nodes' information
    */
    private Customer createUpdatedCustomer(){
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        Address address = createAddress();
        
        Customer updatedCustomer = new Customer(firstName, lastName, phoneNumber, address);
        updatedCustomer.setAccountNum(initialCustomer.getAccountNum());
        return updatedCustomer;
    }
    
    
    /*
    Helper method for createUpdatedCustomer to make code cleaner
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
    
    /*
    Helper method for populateWindow to make code cleaner
    */
    private String getSelectedState(){
        String state = stateComboBox.getValue().toString();
        return state;
    }
    
    /*
    Takes the nodes' current info and creates a Customer object, then 
    updates that customer's info in the database (referenced by the account number)
    */
    private boolean updateCustomerDb(){
        Customer updatedCustomer = createUpdatedCustomer();
        CustomerDAO customerDb = new CustomerDAO();
        return customerDb.updateCustomer(updatedCustomer);
    }
    
    /*
    Checks if the current customer (i.e. tthe last saved information)
    is different from the Customer with the nodes' current info
    */
    private boolean isDifferentCustomerInfo(){
        if (currentCustomer == null){
            return false;
        }
        
        return !currentCustomer.equalsByValue(createUpdatedCustomer());
    }
    
    public String dehyphenatePhoneNumber(String phoneNumber){
        if (phoneNumber.length() == 10){
            return phoneNumber;
        }
        
        String hyphenless = "";
        hyphenless += phoneNumber.substring(0,3);
        hyphenless += phoneNumber.substring(4,7);
        hyphenless += phoneNumber.substring(8, 12);
        
        return hyphenless;
    }
    
    @FXML
    private void clearStatusLabel(){
        this.statusLabel.setText("");
    }
    
}
