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
    
    private boolean fromView;
    
    
    public void initFromView(Customer customer){
        c = customer;
        AccountDAO accountDb = new AccountDAO();
        this.account = accountDb.getByAcctNum(customer.getAccountNum());
        populateWindow();
        fromView = true;
    }
    
    public void initFromSearch(Customer customer){
        c = customer;
        AccountDAO accountDb = new AccountDAO();
        this.account = accountDb.getByAcctNum(customer.getAccountNum());
        populateWindow();
        fromView = false;
    }
        
    private void populateWindow(){
        acctNumTextField.setText(account.getAccountNum());
        balanceTextField.setText(account.getBalanceFormatted());
        interestTextField.setText(account.getInterestRateFormatted());
        loadMonthComboBox();
    }
    
    private void loadMonthComboBox(){
        monthComboBox.setItems(FXCollections.observableArrayList(Month.values()));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultTextField.setDisable(true);
        resultTextField.setEditable(false);
        acctNumTextField.setEditable(false);
        balanceTextField.setEditable(false);
        interestTextField.setEditable(false);
    }
    
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
    
    @FXML
    private void calculateButtonClicked(){
        String interestYTD = account.calculateInterestSoFarFormatted(month);
        
        resultTextField.setText(interestYTD);
        resultTextField.setDisable(false);
    }
    
    
    
    
    
    
        
     
    
}
