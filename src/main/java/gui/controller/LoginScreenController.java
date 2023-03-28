package gui.controller;

import domain.UserController;
import exceptions.EntityDoesntExistException;
import exceptions.IncorrectPasswordException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import util.FXStageUtil;

public class LoginScreenController extends GridPane {
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

	public LoginScreenController(UserController userController) {
		this.userController = userController;
	}

	@FXML
	void SignIn(ActionEvent event) {
		try {
			userController.checkUser(txtEmail.getText(), txtPassword.getText());
			if (userController.userIsAdmin())
				goToHomeAdmin();
			else
				goToHomeWarehouseOperator();
		} catch (NoResultException | IncorrectPasswordException | EntityNotFoundException | EntityDoesntExistException e) {
			showLoginError();
		}
	}

	private void goToHomeWarehouseOperator() {
		FXStageUtil.setScene(LoginScreenController.class.getResource("/gui/OrdersOverview.fxml"), "Overview");
	}

	private void goToHomeAdmin() {
		FXStageUtil.setScene(LoginScreenController.class.getResource("/gui/TransportServiceOverview.fxml"), "Transport Services");
	}
	
	private void showLoginError () {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Login Error");
		alert.setHeaderText(null);
		alert.setContentText("Provided credentials were incorrect!");
		alert.showAndWait();
	}
	
	@FXML
	private void tmpSignInWarhouse() {
		txtEmail.setText("testMagazijnier@mail.com");
		txtPassword.setText("testMagazijnier");
	}	
	
	@FXML
	private void tmpSignInAdmin() {
		txtEmail.setText("testAdmin@mail.com");
		txtPassword.setText("testAdmin");
	}
}
