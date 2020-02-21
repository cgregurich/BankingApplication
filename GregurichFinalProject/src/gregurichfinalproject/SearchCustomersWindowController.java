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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author colin
 */
public class SearchCustomersWindowController implements Initializable {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Label statusLabel;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchButton.setDisable(true);
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
    private void searchButtonClicked(ActionEvent event) throws Exception{
        
        
        
        String first = firstNameTextField.getText();
        String last = lastNameTextField.getText();
        
        if (customerNotFound(first, last)){
            statusLabel.setText("No results found");
            return;
        }
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ViewCustomersWindow.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        ViewCustomersWindowController controller = loader.getController();
        controller.initWithName(first, last);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void checkFields(){
        if (isEitherFieldEmpty()){
            searchButton.setDisable(true);
        }
        else{
            searchButton.setDisable(false);
        }
    }
    
    private boolean isEitherFieldEmpty(){
        return firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty();   
    }
    
    private boolean customerNotFound(String first, String last){
        CustomerDAO customerDb = new CustomerDAO();
        
        return customerDb.getCustomerListByNames(first, last).isEmpty();
    }
    
    @FXML
    private void clearStatusLabel(){
        statusLabel.setVisible(false);
    }
}
