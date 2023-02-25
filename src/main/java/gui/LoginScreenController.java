package gui;

import domain.DomainController;
import exceptions.UserDoesntExistException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginScreenController extends GridPane {

    private DomainController dc;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private TextField userNameTextfield;

    @FXML
    private Button exitApplicationButton;

    public LoginScreenController(DomainController dc) {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("LoginScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        this.dc = dc;
        
        try {
        	loader.load();
        } catch (Exception e) {
			System.out.println(e);
		}
    }

    @FXML
    void login(ActionEvent event) {
        try {
            boolean authenticated = dc.checkUser(userNameTextfield.getText(), passwordTextfield.getText());
            System.out.println("lol");
            if (!authenticated) {
                passwordTextfield.setText("");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wrong password!");
                alert.setHeaderText("You have given the wrong password, try another!");
                alert.showAndWait();
            } else {
                if (dc.userIsAdmin()) {
                    goToAdminScreen();
                } else {
                    goToWarehousmanScreen();
                }
            }

        } catch (UserDoesntExistException e) {
            userNameTextfield.setText("");
            passwordTextfield.setText("");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    private void goToWarehousmanScreen() {
        WarehousemanOverviewScreenController warehousemanOverviewScreenController = new WarehousemanOverviewScreenController(dc);
        Scene scene = new Scene(warehousemanOverviewScreenController);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    private void goToAdminScreen() {
        // TODO

    }


}
