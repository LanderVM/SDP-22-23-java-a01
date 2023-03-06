package gui.controller;

import domain.OrderController;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class HomeWarehouseOperatorController extends AnchorPane {

	@FXML
	private Button btnViewCustomers;
	@FXML
	private Button btnViewOrders;
	@FXML
	private Button btnChangeTrackAndTrace;

	private OrderController orderController;
	private UserController userController;

	public HomeWarehouseOperatorController(OrderController orderController, UserController userController) {
		this.orderController = orderController;
		this.userController = userController;
	}

	@FXML
	void viewCustomers() {
		// TODO
	}

	@FXML
	void viewOrders() {
		AllOrdersOverviewController allOrdersOverviewController = new AllOrdersOverviewController(orderController, userController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AllOrdersOverview.fxml"));
		ChangeStage.change(this, loader, allOrdersOverviewController, "Orders Overview");
	}

	@FXML
	void changeTrackAndTrace() {
		// C
	}
}
