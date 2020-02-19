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
    private TextField monthTextField;
    @FXML
    private TextField resultTextField;
    
    private Customer c;
    private SavingsAccount account;
    
    
    public void initData(Customer customer){
        c = customer;
        AccountDAO accountDb = new AccountDAO();
        this.account = accountDb.getByAcctNum(customer.getAccountNum());
        populateWindow();
    }
        
    private void populateWindow(){
        acctNumTextField.setText(account.getAccountNum());
        balanceTextField.setText(account.getBalanceFormatted());
        interestTextField.setText(account.getInterestRateFormatted());
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
        controller.initData(c);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
        
    }
    
    
        
     
    
}
