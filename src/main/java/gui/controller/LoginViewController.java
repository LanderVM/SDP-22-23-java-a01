package gui.controller;

import domain.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import util.FXStageUtil;

public class LoginViewController extends GridPane {
    @FXML
    public Label lblLogin;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignInAdmin;
    @FXML
    private Button btnSignInWarhouse;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;

    private final UserController userController;

    public LoginViewController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    void SignIn() {
        txtEmail.setText("testAdmin@mail.com");
        txtPassword.setText("testAdmin");
        try {
            userController.checkUser(txtEmail.getText(), txtPassword.getText());
            if (userController.userIsAdmin()) goToHomeAdmin();
            else goToHomeWarehouseOperator();
        } catch (IllegalArgumentException illegalArgumentException) {
            showLoginError(illegalArgumentException.getMessage());
        }
    }

    private void goToHomeWarehouseOperator() {
        FXStageUtil.setScene(LoginViewController.class.getResource("/gui/OrderView.fxml"), "Orders Overview");
    }

    private void goToHomeAdmin() {
        FXStageUtil.setScene(LoginViewController.class.getResource("/gui/CarrierView.fxml"), "Carriers");
    }

    private void showLoginError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
