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
import javafx.scene.control.Button;
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
    private List<SavingsAccount> accountsList = accountDb.getAll();
    
    
    @FXML
    private TextField depositTextField;
    @FXML
    private Button depositButton;
    @FXML
    private TextField withdrawTextField;
    @FXML
    private Button withdrawButton;
    

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
    private void clearStatusLabel(){
        statusLabel.setText("");
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
        
        
        //customer already has an account
        if (isAccountOwner()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error opening account");
            alert.setContentText("Current customer already has an account");
            alert.showAndWait();   
        }
        
        //customer doesn't have an account
        else{
            Customer c = getCurrentCustomer();
            SavingsAccount newAcct = c.openAccount();
            
            accountDb.add(newAcct);
            customerDb.updateAccountNum(c);
        }
        
        displayCurrentCustomer();
    }
    
    
    public boolean isAccountOwner(){
        Customer c = getCurrentCustomer();
        return !(c.getAccountNum() == null);
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
        
        if (isAccountOwner()){
            SavingsAccount fetchedAcct = fetchAccountByAcctNum(c.getAccountNum());
            accountNumTextField.setText(fetchedAcct.getAccountNum());
            balanceTextField.setText(fetchedAcct.getBalanceFormatted());
            accountNumTextField.setDisable(false);
            balanceTextField.setDisable(false);
            depositTextField.setDisable(false);
            withdrawTextField.setDisable(false);
            depositButton.setDisable(false);
            withdrawButton.setDisable(false);
            
        }
        
        
        else{
            accountNumTextField.setText("No account found");
            balanceTextField.setText("No account found");
            accountNumTextField.setDisable(true);
            balanceTextField.setDisable(true);
            depositTextField.setDisable(true);
            withdrawTextField.setDisable(true);
            depositButton.setDisable(true);
            withdrawButton.setDisable(true);
            
        }
    }
    
    private SavingsAccount fetchAccountByAcctNum(String acctNum){
        refreshAccountsList();
        
        for (SavingsAccount a : accountsList){
            
            if (a.getAccountNum().equals(acctNum)){
                return a;
            }
        }
        return null;
    }
    
    private void refreshAccountsList(){
        this.accountsList = accountDb.getAll();
    }
    
    @FXML
    private void depositButtonClicked(){
        //check if entry is valid
        if (isDepositValid()){
            Double amount = Double.parseDouble(depositTextField.getText());
            if (depositToAccount(amount)){
                refreshAccountsList();
                statusLabel.setText("Deposit successful");
                displayCurrentCustomer();
                clearDepositAndWithdrawFields();
            }
            else{
                System.out.println("Deposit failed");
            }
        }
    }
    
    private boolean isDepositValid(){
        String depositStr = depositTextField.getText();
        try{
            double deposit = Double.parseDouble(depositStr);
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid deposit amount");
            alert.setContentText("Deposit amount must be in decimal format");
            alert.showAndWait();
            return false;
        }
        
        return true;
    }
    
    private boolean depositToAccount(Double amount){
        Customer c = getCurrentCustomer();
        
        SavingsAccount curAcct = fetchAccountByAcctNum(c.getAccountNum());
        curAcct.deposit(amount);
        return accountDb.updateBalance(curAcct);
    }
    
    
    private void clearDepositAndWithdrawFields(){
        depositTextField.setText("");
        withdrawTextField.setText("");
    }
    
    
    
}
