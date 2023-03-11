package gui.controller;

import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
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
	private TransportServiceController transportServiceController;
	
	public HomeWarehouseOperatorController(OrderController orderController, UserController userController, TransportServiceController transportServiceController) {
		this.orderController = orderController;
		this.userController = userController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	void viewCustomers() {
		
	}

	@FXML
	void viewOrders() {
	}

	@FXML
	void changeTrackAndTrace() {

	}
}
