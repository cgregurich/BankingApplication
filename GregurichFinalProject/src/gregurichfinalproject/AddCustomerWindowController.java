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
    
    //TODO
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
        
        loadStateChoiceBox();
    }   
    
    @FXML
    private void addCustomerButtonClicked(){
        if (isMissingFields()){
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
    
    @FXML
    private void backButtonClicked(ActionEvent event) throws Exception{
         Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
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
        a.setStreetAddress(address);
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
