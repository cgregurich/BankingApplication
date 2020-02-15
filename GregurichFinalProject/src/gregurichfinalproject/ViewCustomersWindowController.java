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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
    @FXML
    private TextField accountNumTextField;
    @FXML
    private TextField balanceTextField;
    @FXML
    private Label statusLabel;
    
    private CustomerDAO customerDb = new CustomerDAO();
    
    private List<Customer> customersList = customerDb.getAll();
    private int currentCustomersIndex = 0;
    
    private AccountDAO accountDb = new AccountDAO();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusLabel.setText("");
        
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        addressTextField.setEditable(false);
        cityStateZipTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        accountNumTextField.setEditable(false);
        balanceTextField.setEditable(false);
        
        
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
    private void openAccountButtonClicked(){
        if (!isNewAccount()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error opening account");
            alert.setContentText("Current customer already has an account");
            alert.showAndWait();   
        }
        
        else{
            Customer c = getCurrentCustomer();
            c.openAccount();
            balanceTextField.setDisable(false);
            
            accountDb.add(c.getAccount());
            customerDb.update(c, "accountNum");
            
        }
        
        displayCurrentCustomer();
    }
    
    
    public boolean isNewAccount(){
        Customer c = getCurrentCustomer();
        
        return c.getAccount() == null;
    }
    
    
    @FXML
    private void previousCustomerButtonClicked(){
        if (customersList.isEmpty()){
            statusLabel.setText("No customers found");
        }
        
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
        if (customersList.isEmpty()){
            statusLabel.setText("No customers found");
        }
        
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
    
    private Customer getCurrentCustomer(){
        Customer c = customersList.get(currentCustomersIndex);
        return c;
    }
    
    private void displayCurrentCustomer(){
        
        if (customersList.isEmpty()){
            statusLabel.setText("No customers found");
            return;
        }
        
        Customer c = getCurrentCustomer();
        
        firstNameTextField.setText(c.getFirstName());
        lastNameTextField.setText(c.getLastName());
        addressTextField.setText(c.getAddress().getFullStreetAddress());
        cityStateZipTextField.setText(c.getAddress().getCityStateZip());
        phoneNumberTextField.setText(c.getPhoneNumber());
        
        
        if (c.getAccount() == null){
            accountNumTextField.setText("No account found");
            balanceTextField.setText("");
            balanceTextField.setDisable(true);
        }
        else{
            accountNumTextField.setText(c.getAccountNum());
            balanceTextField.setDisable(false);
            balanceTextField.setText(c.getAccount().getBalanceFormatted());
        }
        
        
    }
    
}
