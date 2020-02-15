/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author colin
 */
public class ViewCustomersWindowController implements Initializable {
    
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cityStateZipTextField;
    @FXML
    private TextField phoneNumberTextField;
    private CustomerDAO customerDb = new CustomerDAO();
    
    private List<Customer> customersList = customerDb.getAll();
    private int currentCustomersIndex = 0;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        addressTextField.setEditable(false);
        cityStateZipTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        
        
        displayCurrentCustomer();
        
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
    private void previousCustomerButtonClicked(){
        if (currentCustomersIndex == -1){
            currentCustomersIndex = 0;
        }
        
        else if (currentCustomersIndex == 0){
            currentCustomersIndex = customersList.size() - 1;
        }
        
        else{
            currentCustomersIndex--;
        }
        
        displayCurrentCustomer();
    }
    
    @FXML
    private void nextCustomerButtonClicked(){
        if (currentCustomersIndex == -1){
            currentCustomersIndex = 0;
        }
        
        else if (currentCustomersIndex == customersList.size() - 1){
            currentCustomersIndex = 0;
        }
        
        else{
            currentCustomersIndex++;
        }
        
        displayCurrentCustomer();
    }
    
    private void displayCurrentCustomer(){
        Customer c = customersList.get(currentCustomersIndex);
        
        firstNameTextField.setText(c.getFirstName());
        lastNameTextField.setText(c.getLastName());
        addressTextField.setText(c.getAddress().getStreetAddress());
        cityStateZipTextField.setText(c.getAddress().getCityStateZip());
        phoneNumberTextField.setText(c.getPhoneNumber());
    }
    
}
