/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
public class CalculateInterestWindowController implements Initializable {

    @FXML
    private TextField acctNumTextField;
    @FXML
    private TextField balanceTextField;
    @FXML
    private TextField interestTextField;
    
    @FXML
    private ComboBox monthComboBox;
    @FXML
    private TextField resultTextField;
    
    private Customer c;
    private SavingsAccount account;
    
    private Month month;
    
    //bool to know where the user originally came from: search or view all
    private boolean fromView;
    
    
    /*
    method called when user clicked on calculate interest from the view all window
    Populates the window with the param's information
    */
    public void initFromView(Customer customer){
        c = customer;
        AccountDAO accountDb = new AccountDAO();
        this.account = accountDb.getByAcctNum(customer.getAccountNum());
        populateWindow();
        fromView = true;
    }
    
    /*
    method called when user clicked on calculate interest from the results of a search
    Populates the window with the param's information
    */
    public void initFromSearch(Customer customer){
        c = customer;
        AccountDAO accountDb = new AccountDAO();
        this.account = accountDb.getByAcctNum(customer.getAccountNum());
        populateWindow();
        fromView = false;
    }
        
    /*
    sets the node's to the account's information
    */
    private void populateWindow(){
        acctNumTextField.setText(account.getAccountNum());
        balanceTextField.setText(account.getBalanceFormatted());
        interestTextField.setText(account.getInterestRateFormatted());
        loadMonthComboBox();
    }
    
    /*
    fills the combo box with all the values of enum Month
    */
    private void loadMonthComboBox(){
        monthComboBox.setItems(FXCollections.observableArrayList(Month.values()));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultTextField.setDisable(true);
        resultTextField.setEditable(false);
        acctNumTextField.setEditable(false);
        balanceTextField.setEditable(false);
        interestTextField.setEditable(false);
    }
    
    /*
    returns to the view window
    Depending on value of fromView, it will display all customers, or only the ones 
    that match the customer's first and last name
    */
    @FXML
    private void backButtonClicked(ActionEvent event) throws Exception{
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ViewCustomersWindow.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        ViewCustomersWindowController controller = loader.getController();
        
        if (fromView){
            controller.initData(c);
        }
        else{
            controller.initWithName(c.getFirstName(), c.getLastName());
        }

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
    
    
    @FXML
    private void monthSelected(){
        this.month = Month.valueOf(monthComboBox.getValue().toString());
    }
    
    /*
    calculates the customer's interest based off their selected month
    */
    @FXML
    private void calculateButtonClicked(){
        String interestYTD = account.calculateInterestSoFarFormatted(month);
        
        resultTextField.setText(interestYTD);
        resultTextField.setDisable(false);
    }
    
    
    
    
    
    
        
     
    
}
