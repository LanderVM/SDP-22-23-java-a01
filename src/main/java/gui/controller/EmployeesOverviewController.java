package gui.controller;

import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import util.FXStageUtil;

public class EmployeesOverviewController {
	@FXML
	private Label lblUser;	

	private final OrderController orderController;
	private final UserController userController;
	private final SupplierController supplierController;
	private final TransportServiceController transportServiceController;

	public EmployeesOverviewController(OrderController orderController, UserController userController,
			SupplierController supplierController, TransportServiceController transportServiceController) {

		this.orderController = orderController;
		this.userController = userController;
		this.supplierController = supplierController;
		this.transportServiceController = transportServiceController;
	}
	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
	}

	@FXML
	private void showTransportservices() {
		TransportServiceOverviewController transportServiceOverviewController = new TransportServiceOverviewController(
				orderController, userController, supplierController, transportServiceController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TransportServiceOverview.fxml"));
		FXStageUtil.change(loader, transportServiceOverviewController, "Transport Service Overview");
	}
	

	@FXML
	private void showBoxes() {
		PackagingOverviewController packagingOverviewController = new PackagingOverviewController(orderController, userController,
				transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PackagingOverview.fxml"));
		FXStageUtil.change(loader, packagingOverviewController, "Packaging Overview");
	}

	@FXML
	private void logOut() {
		LoginScreenController loginScreenController = new LoginScreenController(orderController, userController,
				transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginScreen.fxml"));
		FXStageUtil.change(loader, loginScreenController, "Log In");
	}
}
