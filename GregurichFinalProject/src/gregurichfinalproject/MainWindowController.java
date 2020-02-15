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
 
 *
 * @author colin
 */
public class MainWindowController implements Initializable {
    
    
    @FXML Button exitButton;
    
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void addCustomerButtonClicked(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomerWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void viewCustomersButtonClicked(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ViewCustomersWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
        
    }
    
    @FXML
    private void exitButtonClicked(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        
        stage.close();
    }
            
}
