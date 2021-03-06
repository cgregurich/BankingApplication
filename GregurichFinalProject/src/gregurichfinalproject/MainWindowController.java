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
    
    /*
    opens the add customer window
    */
    @FXML
    private void addCustomerButtonClicked(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomerWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    /*
    opens the view customers window
    */
    @FXML
    private void viewCustomersButtonClicked(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ViewCustomersWindow.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        ViewCustomersWindowController controller = loader.getController();
        controller.initWithName(null, null);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
        
    }
    
    /*
    opens the search customers window
    */
    @FXML
    private void searchCustomersButtonClicked(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SearchCustomersWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    /*
    closes the program
    */
    @FXML
    private void exitButtonClicked(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        
        stage.close();
    }
    
    
            
}
