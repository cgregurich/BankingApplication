/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;


import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
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
public class ViewCustomersWindowController implements Initializable {
    
    
    @FXML
    private Label statusLabel;
    @FXML
    private Label amountLabel;
    
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    
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
    
    @FXML
    private Button openAccountButton;
    
    @FXML
    private Button calculateInterestButton;
    
    
    
   
    public void initData(Customer currentCustomer){
        for (Customer c : customersList){
            if (c.getAccountNum().equals(currentCustomer.getAccountNum())){
                this.currentCustomersIndex = this.customersList.indexOf(c);
                break;
            }
            else{
                this.currentCustomersIndex = 0;
            }
        }
        
        displayCurrentCustomer();
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        statusLabel.setText("");
        amountLabel.setText("");
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
    
    private void refreshCustomerList(){
        this.customersList = customerDb.getAll();
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
            SavingsAccount curAcct = getCurrentAccount();
            accountNumTextField.setText(curAcct.getAccountNum());
            balanceTextField.setText(curAcct.getBalanceFormatted());
            accountNumTextField.setDisable(false);
            balanceTextField.setDisable(false);
            depositTextField.setDisable(false);
            withdrawTextField.setDisable(false);
            depositButton.setDisable(false);
            withdrawButton.setDisable(false);
            openAccountButton.setDisable(true);
            calculateInterestButton.setDisable(false);
            
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
            openAccountButton.setDisable(false);
            calculateInterestButton.setDisable(true);
            
            
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
    
    private SavingsAccount getCurrentAccount(){
        Customer c = getCurrentCustomer();
        SavingsAccount a = fetchAccountByAcctNum(c.getAccountNum());
        return a;
    }
    
    private void refreshAccountsList(){
        this.accountsList = accountDb.getAll();
    }
    
    @FXML
    private void withdrawButtonClicked(){
        if (isWithdrawNumber()){
            Double amount = Double.parseDouble(withdrawTextField.getText());
            if (isWithdrawValid(amount)){
                refreshAccountsList();
                statusLabel.setText("Withdrawal successful!");
                amountLabel.setText("Amount: " +formatAmount(amount));
                displayCurrentCustomer();
                clearDepositAndWithdrawFields();
            }
            
        }   
    }
    
    private boolean isWithdrawValid(Double amount){
        return isAmountValid(amount) && !isAmountZero(amount) && withdrawFromAccount(amount);
    }
    
    private boolean withdrawFromAccount(Double amount){
        SavingsAccount curAcct = getCurrentAccount();
        if (curAcct.withdraw(amount)){
            return accountDb.updateBalance(curAcct);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Withdrawal failed");
            alert.setContentText("Insufficient funds");
            alert.showAndWait();
            return false;
        }
    }
    
    
    
    private boolean isWithdrawNumber(){
        String withdrawStr = withdrawTextField.getText();
        try{
            Double withdraw = Double.parseDouble(withdrawStr);
            return true;
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid withdraw amount");
            alert.setContentText("Withdraw amount must be in valid currency format");
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    private void depositButtonClicked(){
        //check if entry is valid
        if (isDepositNumber()){ 
            Double amount = Double.parseDouble(depositTextField.getText());
            if (isDepositValid(amount)){
                refreshAccountsList();
                statusLabel.setText("Deposit successful!");
                amountLabel.setText("Amount: " +formatAmount(amount));
                
                displayCurrentCustomer();
                clearDepositAndWithdrawFields();
            }
            else{
                statusLabel.setText("Deposit failed");
            }
        }
    }
    
    private boolean isDepositValid(Double amount){
        return isAmountValid(amount) && !isAmountZero(amount) && depositToAccount(amount);
    }
    
    private boolean isDepositNumber(){
        String depositStr = depositTextField.getText();
        try{
            double deposit = Double.parseDouble(depositStr);
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid deposit amount");
            alert.setContentText("Deposit amount must be in valid currency format");
            alert.showAndWait();
            return false;
        }
       
        return true;
    }
    
    private boolean depositToAccount(Double amount){
        SavingsAccount curAcct = getCurrentAccount();
        curAcct.deposit(amount);
        return accountDb.updateBalance(curAcct);
    }
    
    private String formatAmount(Double amount){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(amount);
    }
    
    
    @FXML
    private void clearLabels(){
        statusLabel.setText("");
        amountLabel.setText("");
    }
    
    private void clearAmountLabel(){
        amountLabel.setText("");
    }
    
    private void clearDepositAndWithdrawFields(){
        depositTextField.setText("");
        withdrawTextField.setText("");
    }
    
    
    @FXML
    private void clearLabelsAndFields(){
        clearLabels();
        clearAmountLabel();
        clearDepositAndWithdrawFields();
    }
    
    private boolean isAmountValid(Double amount){
        if (BigDecimal.valueOf(amount).scale() <= 2){
            return true;
        }
       
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid deposit amount");
            alert.setContentText("Deposit amount must be in valid currency format");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean isAmountZero(Double amount){
        if (amount == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid deposit amount");
            alert.setContentText("Amount must can't be 0.");
            alert.showAndWait();
            return true;
        }
        return false;
    }
    
    @FXML
    private void updateInfoButtonClicked(ActionEvent event) throws Exception{
        if (!this.isAccountOwner()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error updating info");
            alert.setContentText("Only account owners can update info");
            alert.showAndWait();
            return;
        }
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UpdateInfoWindow.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        UpdateInfoWindowController controller = loader.getController();
        Customer c = getCurrentCustomer(); 
        controller.initData(c);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void calculateInterestButtonClicked(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CalculateInterestWindow.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        CalculateInterestWindowController controller = loader.getController();
        Customer c = getCurrentCustomer();
        controller.initData(c);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    
}
