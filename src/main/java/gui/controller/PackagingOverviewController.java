package gui.controller;

import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import util.FXStageUtil;

public class PackagingOverviewController {
	@FXML
	private Label lblUser;
	@FXML
	private TableView<PackagingOverviewController> tblBoxes;

	private final OrderController orderController;
	private final UserController userController;
	private final SupplierController supplierController;
	private final TransportServiceController transportServiceController;

	public PackagingOverviewController(OrderController orderController, UserController userController,
									   TransportServiceController transportServiceController, SupplierController supplierController) {

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
	private void showEmployees() {
		EmployeesOverviewController employeesOverviewController = new EmployeesOverviewController(
				orderController, userController, supplierController, transportServiceController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/EmployeesOverview.fxml"));
		FXStageUtil.change(loader, employeesOverviewController, "Employees Overview");		
	}

	@FXML
	private void showTransportservices() {
		TransportServiceOverviewController transportServiceOverviewController = new TransportServiceOverviewController(
				orderController, userController, supplierController, transportServiceController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TransportServiceOverview.fxml"));
		FXStageUtil.change(loader, transportServiceOverviewController, "Transport Service Overview");
	}

	@FXML
	private void addBox() {
	}

	@FXML
	private void deleteBox() {
	}

	@FXML
	private void logOut() {
		LoginScreenController loginScreenController = new LoginScreenController(orderController, userController,
				transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginScreen.fxml"));
		FXStageUtil.change(loader, loginScreenController, "Log In");
	}

}
