package gui;

import java.io.IOException;

import domain.DomainController;
import exceptions.UserDoesntExistException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginScreenController extends AnchorPane {

    private DomainController dc;

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;


    public LoginScreenController(DomainController dc) {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/LoginScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void SignIn(ActionEvent event) {
        try {
            boolean authenticated = dc.checkUser(txtEmail.getText(), txtPassword.getText());
            if (!authenticated) {
                txtPassword.setText("");
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
            txtEmail.setText("");
            txtPassword.setText("");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void goToWarehousmanScreen() {
        /*WarehousemanOverviewScreenController warehousemanOverviewScreenController = new WarehousemanOverviewScreenController(dc);
        Scene scene = new Scene(warehousemanOverviewScreenController,600,600);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/


    }

    private void goToAdminScreen() {
        Test test = new Test(dc);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/test.fxml"));
        loader.setRoot(test);
        loader.setController(test);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(test, 600, 600);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


}
