/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject.Controllers;


import gregurichfinalproject.Customer;
import gregurichfinalproject.DataAccessObjects.CustomerDAO;
import gregurichfinalproject.DataAccessObjects.AccountDAO;
import gregurichfinalproject.SavingsAccount;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author colin
 */

/*
this class is used both for displaying all customers (through View All choice)
and for displaying search results (through Search Customers choice)
*/

public class ViewCustomersWindowController implements Initializable {
    
    
    @FXML
    private Label statusLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label resultsLabel;
    
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
    
    //list to hold all customers, in order to step through them
    private List<Customer> customersList = customerDb.getAll();
    private int currentCustomersIndex = 0;
    
    private AccountDAO accountDb = new AccountDAO();
    private List<SavingsAccount> accountsList = accountDb.getAll();
    
    private boolean refreshLocal = false;
    
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
    
    @FXML
    private Button updateInfoButton;
    
    @FXML
    private Button deleteCustomerButton;
    
    private String searchedFirst;
    private String searchedLast;
    private boolean fromSearch;
    
    
    
   /*
    method used to "remember" what the last Customer was when opening a new window
    (eg. calculate interest or update info)
    */
    public void initData(Customer currentCustomer){
        for (Customer c : customersList){
            if (isAccountOwner(c) && c.getAccountNum().equals(currentCustomer.getAccountNum())){
                this.currentCustomersIndex = this.customersList.indexOf(c);
                break;
            }
            else{
                this.currentCustomersIndex = 0;
            }
        }
        
        this.fromSearch = false;
        
        displayCurrentCustomer();
    }
    
    /*
    method used to display only Customers with given first and last name
    i.e. originally user searched for customers by name, not by clicking view all
    */
    public void initWithName(String first, String last){
        if (first == null || last == null){
            displayCurrentCustomer();
            return;
        }
        
        this.fromSearch = true;
        
        this.searchedFirst = first;
        this.searchedLast = last;
        this.customersList = customerDb.getCustomerListByNames(first, last);
        
        if (this.customersList.size() > 1){
            resultsLabel.setText("Multiple results with name " +first+ " " +last);
        }
        else{
            previousButton.setDisable(true);
            nextButton.setDisable(true);
        }
        
        this.currentCustomersIndex = 0;
        this.refreshLocal = true;
        displayCurrentCustomer();
    }
    

    
    /*
    clears all labels and makes all fields uneditable
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusLabel.setText("");
        amountLabel.setText("");
        resultsLabel.setText("");
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        addressTextField.setEditable(false);
        cityStateZipTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        accountNumTextField.setEditable(false);
        balanceTextField.setEditable(false);
    }    
    
    
    
    /*
    goes back to whatever window user originally was on
    i.e. search or view all
    */
    @FXML
    private void backButtonClicked(ActionEvent event) throws Exception{
        if (fromSearch){
            Parent root = FXMLLoader.load(getClass().getResource("SearchCustomersWindow.fxml"));
        
            Scene scene = new Scene(root);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }
        
        else{
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        
            Scene scene = new Scene(root);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        
            
    }
    
    /*
    opens an account for the current customer
    No error message is needed if customer already has an account
    because open account button is disabled for account owners
    */
    @FXML
    private void openAccountButtonClicked(){
        Customer c = getCurrentCustomer();
        SavingsAccount newAcct = c.openAccount();

        accountDb.add(newAcct);
        customerDb.updateAccountNum(c);
        
        displayCurrentCustomer();
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
    
    
    public boolean isAccountOwner(Customer c){
        return !(c.getAccountNum() == null);
    }
    
    
    
    
    private void refreshCustomerList(){
        if (refreshLocal){
            this.customersList = customerDb.getCustomerListByNames(searchedFirst, searchedLast);
        }
        
        else{
            this.customersList = customerDb.getAll();
        }
            
        
    }
    /*
    returns the Customer in the list at the current index
    */
    private Customer getCurrentCustomer(){
        Customer c = customersList.get(currentCustomersIndex);
        return c;
    }
    
    /*
    displays the current customer
    if there are no customers in the DB, then all nodes and disabled and 
    it tells the user no customers are found
    Otherwise displays the current customer
    If the customer has an account, displays account information
    If not, account information nodes are disabled
    */
    private void displayCurrentCustomer(){
        
        if (customersList.isEmpty()){
            clearInfoFields();
            clearLabelsAndFields();
            firstNameTextField.setDisable(true);
            lastNameTextField.setDisable(true);
            addressTextField.setDisable(true);
            cityStateZipTextField.setDisable(true);
            phoneNumberTextField.setDisable(true);
            accountNumTextField.setDisable(true);
            balanceTextField.setDisable(true);
            depositTextField.setDisable(true);
            withdrawTextField.setDisable(true);
            
            statusLabel.setText("No customers found");
            openAccountButton.setDisable(true);
            depositButton.setDisable(true);
            withdrawButton.setDisable(true);
            calculateInterestButton.setDisable(true);
            updateInfoButton.setDisable(true);
            nextButton.setDisable(true);
            previousButton.setDisable(true);
            deleteCustomerButton.setDisable(true);
            return;
        }
        
        Customer c = getCurrentCustomer();
        
        
        firstNameTextField.setText(c.getFirstName());
        lastNameTextField.setText(c.getLastName());
        addressTextField.setText(c.getAddress().getFullStreetAddress());
        cityStateZipTextField.setText(c.getAddress().getCityStateZip());
        phoneNumberTextField.setText(c.getPhoneNumber());
        
        if (isAccountOwner(getCurrentCustomer())){
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
    
    /*
    returns the SavingsAccount with the given account number
    */
    private SavingsAccount fetchAccountByAcctNum(String acctNum){
        
        refreshAccountsList();
        
        for (SavingsAccount a : accountsList){
            
            if (a.getAccountNum().equals(acctNum)){
                return a;
            }
        }
        return null;
    }
    
    
    /*
    returns the SavingsAccount of the current customer 
    */
    private SavingsAccount getCurrentAccount(){
        Customer c = getCurrentCustomer();
        SavingsAccount a = fetchAccountByAcctNum(c.getAccountNum());
        return a;
    }
    
    /*
    reloads the list of all accounts; this is used when a deposit or withdrawal
    has occurred
    */
    private void refreshAccountsList(){
        this.accountsList = accountDb.getAll();
    }
    
    /*
    withdraws money from the customer's account if it possible
    If it is, displays a successful message
    */
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
    
    /*
    checks validity of withdraw amount
    */
    private boolean isWithdrawValid(Double amount){
        return isAmountValid(amount) && !isAmountZero(amount) && withdrawFromAccount(amount);
    }
    
    /*
    attempts to withdraw the given amount from the current customers account
    if it fails, displays an error message
    */
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
    
    /*
    helper method to validate withdraw entry
    */
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
    
    /*
    deposits money to the customer's account
    Displays a message depending on success or failure
    */
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
    
    /*
    checks validity of deposit amount
    */
    private boolean isDepositValid(Double amount){
        return isAmountValid(amount) && !isAmountZero(amount) && depositToAccount(amount);
    }
    
    /*
    helper method to validate that the deposit amount contains only numbers
    */
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
    
    /*
    deposits the given amount to the current customer's account
    */
    private boolean depositToAccount(Double amount){
        SavingsAccount curAcct = getCurrentAccount();
        curAcct.deposit(amount);
        return accountDb.updateBalance(curAcct);
    }
    
    /*
    returns a formatted String of the given amount in currency format
    */
    private String formatAmount(Double amount){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(amount);
    }
    
    @FXML
    private void clearLabels(){
        statusLabel.setText("");
        amountLabel.setText("");
        resultsLabel.setText("");
    }
    
    private void clearAmountLabel(){
        amountLabel.setText("");
    }
    
    private void clearDepositAndWithdrawFields(){
        depositTextField.setText("");
        withdrawTextField.setText("");
    }
    
    private void clearInfoFields(){
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        cityStateZipTextField.clear();
        phoneNumberTextField.clear();
        accountNumTextField.clear();
        balanceTextField.clear();
    }
    
    @FXML
    private void clearLabelsAndFields(){
        clearLabels();
        clearAmountLabel();
        clearDepositAndWithdrawFields();
    }
    
    /*
    helper method to validate the decimal places of given amount
    (must be 3 decimal places or less)
    */
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
    
    /*
    helper method to validate that the given amount isn't zero
    */
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
    
    /*
    opens the update info window
    If user originally searched, it calls the appropriate method to remember 
    the name that was searched
    If originally from view all, it calls the appropriate method to remember to display
    all customers when going back
    */
    @FXML
    private void updateInfoButtonClicked(ActionEvent event) throws Exception{
        if (!this.isAccountOwner(getCurrentCustomer())){
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
        
        if (fromSearch){
            controller.initFromSearch(c);
        }
        else{
            controller.initFromView(c);
        }
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    /*
    opens the calculate interest window
    If user originally searched, it calls the appropriate method to remember 
    the name that was searched
    If originally from view all, it calls the appropriate method to remember to display
    all customers when going back
    */
    @FXML
    private void calculateInterestButtonClicked(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CalculateInterestWindow.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        CalculateInterestWindowController controller = loader.getController();
        Customer c = getCurrentCustomer();
        
        if (fromSearch){
            controller.initFromSearch(c);
        }
        
        else{
            controller.initFromView(c);
        }
        
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    /*
    deletes the current customer
    First prompts user to confirm their choice
    If user says yes, then customer is deleted from the database (and their account,
    if they had one, is deleted from the accounts database)
    Everything is then refreshed and the customers are displayed again
    */
    @FXML
    private void deleteCustomerButtonClicked(ActionEvent event) throws Exception{
        Alert alert = new Alert(AlertType.CONFIRMATION, "Delete this customer and account?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.YES){
            
            if (isAccountOwner(getCurrentCustomer())){
                deleteCurrentAccount();
                deleteCurrentCustomer();
            }
            else{
                deleteCurrentCustomer();
            }
            
            if (currentCustomersIndex > 0){
                currentCustomersIndex--;
            }
                
            
            refreshCustomerList();
            displayCurrentCustomer();
        }
    }
    
    /*
    helper method to delete the current customer from the database
    */
    private void deleteCurrentCustomer(){
        customerDb.deleteCustomer(getCurrentCustomer());
        
    }
    
    /*
    helper method to delete the current customer's account from the database
    */
    private void deleteCurrentAccount(){
        accountDb.deleteAccount(getCurrentAccount());
    }
}
