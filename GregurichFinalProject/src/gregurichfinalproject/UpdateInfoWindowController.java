/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author colin
 */
public class UpdateInfoWindowController implements Initializable {
    
    private Customer c;
    
    
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
    
    
    
    
    public void initData(Customer customer){
        this.c = customer;
        populateWindow();
        
    }
    
    private void populateWindow(){
        firstNameTextField.setText(c.getFirstName());
        lastNameTextField.setText(c.getLastName());
        phoneNumberTextField.setText(c.getPhoneNumber());
        streetAddressTextField.setText(c.getAddress().getStreetAddress());
        secondaryAddressTextField.setText(c.getAddress().getAptNum());
        cityTextField.setText(c.getAddress().getCity());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void backButtonClicked(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ViewCustomersWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
}
