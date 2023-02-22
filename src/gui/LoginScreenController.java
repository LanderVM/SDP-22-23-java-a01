package gui;

import domain.DomainController;
import exceptions.UserDoesntExistException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginScreenController extends GridPane {
	
	private DomainController dc;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private TextField userNameTextfield;

    @FXML
    void createAccount(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) {
    	try {
    		boolean authenticated = dc.checkUser(userNameTextfield.getText(), passwordTextfield.getText());
    		if (!authenticated) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Wrong password!");
        		alert.setHeaderText("You have given the wrong password, try another!");
        		alert.showAndWait();
    		} else {
    			goToWarehousmanScreen();
    		}
    		
    	} catch (UserDoesntExistException e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error!");
    		alert.setHeaderText(e.getMessage());
    		alert.showAndWait();
    	}
    }

	private void goToWarehousmanScreen() {
		
	}

}
