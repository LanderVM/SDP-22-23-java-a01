package gui.controller;

import domain.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import util.FXStageUtil;

public class PackagingOverviewController {
	@FXML
	private Label lblUser;
	@FXML
	private TableView<PackagingOverviewController> tblBoxes;

	private final UserController userController;

	public PackagingOverviewController(UserController userController) {
		this.userController = userController;
	}

	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
	}

	@FXML
	private void showEmployees() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/EmployeesOverview.fxml"), "Employees");
	}

	@FXML
	private void showTransportservices() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/TransportServiceOverview.fxml"), "Transport Services");
	}

	@FXML
	private void addBox() {
	}

	@FXML
	private void deleteBox() {
	}

	@FXML
	private void logOut() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
	}

}
