package gui;

import java.io.IOException;

import domain.OrderController;
import domain.UserController;
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

    private final OrderController orderController;
    private final UserController userController;

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;


    public LoginScreenController(OrderController orderController, UserController userController) {
        this.orderController = orderController;
        this.userController = userController;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/LoginScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SignIn(ActionEvent event) {
        try {
            boolean authenticated = userController.checkUser(txtEmail.getText(), txtPassword.getText());
            if (!authenticated) {
                txtPassword.setText("");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wrong password");
                alert.setHeaderText(null);
                alert.setContentText("The password entered is incorrect");
                alert.showAndWait();
            } else {
                if (userController.userIsAdmin()) {
                    goToAdminScreen();
                } else {
                    goToWarehousmanScreen();
                }
            }
        } catch (UserDoesntExistException e) {
            //txtEmail.setText("");
            txtPassword.setText("");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
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
        HomeAdminController homeAdminController = new HomeAdminController(orderController);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeAdmin.fxml"));
        loader.setRoot(homeAdminController);
        loader.setController(homeAdminController);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(homeAdminController, 600, 300);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }


}
