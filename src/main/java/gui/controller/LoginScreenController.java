package gui.controller;

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
import util.FXStageUtil;

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
	private final SupplierController supplierController;

	public LoginScreenController(OrderController orderController,
								 UserController userController,
								 TransportServiceController transportServiceController,
								 SupplierController supplierController) {
		this.orderController = orderController;
		this.userController = userController;
		this.transportServiceController = transportServiceController;
		this.supplierController = supplierController;
	}

	@FXML
	void SignIn(ActionEvent event) {
		try {
			userController.checkUser(txtEmail.getText(), txtPassword.getText());
			if (userController.userIsAdmin())
				goToHomeAdmin();
			else
				goToHomeWarehouseOperator();
		} catch (NoResultException | IncorrectPasswordException e) {
			showLoginError();
		}
	}

	private void goToHomeWarehouseOperator() {
		OrdersOverviewController ordersOverviewController = new OrdersOverviewController(orderController, userController, transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrdersOverview.fxml"));
		FXStageUtil.change(loader, ordersOverviewController, "Orders Overview");
	}

	private void goToHomeAdmin() {
		TransportServiceOverviewController transportServiceOverviewController = new TransportServiceOverviewController(orderController, userController, supplierController, transportServiceController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TransportServiceOverview.fxml"));
		FXStageUtil.change(loader, transportServiceOverviewController, "Transport Service Overview");
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
