package gui.controller;

import java.io.IOException;

import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import exceptions.IncorrectPasswordException;
import jakarta.persistence.NoResultException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginScreenController extends GridPane {

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

	private final OrderController orderController;
	private final UserController userController;
	private final TransportServiceController transportServiceController;
	private final SupplierController sc;

	public LoginScreenController(OrderController orderController, UserController userController,
			TransportServiceController transportServiceController, SupplierController sc) {
		this.orderController = orderController;
		this.userController = userController;
		this.transportServiceController = transportServiceController;
		this.sc = sc;
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
			userController.checkUser(txtEmail.getText(), txtPassword.getText());
			if (userController.userIsAdmin())
				goToHomeAdmin();
			else
				goToHomeWarehouseOperator();
		} catch (NoResultException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User not found");
			alert.setHeaderText(null);
			alert.setContentText("No user was found with the provided email address.");
			alert.showAndWait();
		} catch (IncorrectPasswordException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("The password provided was incorrect.");
			alert.showAndWait();
		}
	}

	private void goToHomeWarehouseOperator() {
		OrdersOverviewController ordersOverviewController = new OrdersOverviewController(orderController, userController, transportServiceController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrdersOverview.fxml"));
		ChangeStage.change(this, loader, ordersOverviewController, "Overview");
	}

	private void goToHomeAdmin() {
		TransportServicesOverviewController transportServicesOverviewController = new TransportServicesOverviewController(orderController, userController, sc);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TransportServicesOverview.fxml"));
		ChangeStage.change(this, loader, transportServicesOverviewController, "Orders Overview");
	}
	
	@FXML
	private void TmpSignInWarhouse() {
		txtEmail.setText("testMagazijnier@mail.com");
		txtPassword.setText("testMagazijnier");
		SignIn(null);
	}	
	
	@FXML
	private void TmpSignInAdmin() {
		txtEmail.setText("testAdmin@mail.com");
		txtPassword.setText("testAdmin");
		SignIn(null);
	}

}
