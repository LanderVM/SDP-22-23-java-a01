package gui.controller;

import domain.OrderController;
import domain.SupplierController;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import util.ChangeStage;

public class TransportServicesOverviewController extends GridPane {

	@FXML
	private Button btnSave;

	@FXML
	private Button btnAdd;
	
	@FXML 
	private Label lblUser;

	private OrderController orderController;
	private UserController userController;
	private SupplierController sc;
	
	public TransportServicesOverviewController(OrderController orderController, UserController userController, SupplierController sc) {
		this.orderController = orderController;
		this.userController = userController;
		this.sc = sc;
	}


	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
		
	}
	
	@FXML
	private void Save() {
	}

	@FXML
	private void Add() {}
	
	@FXML
	private void showHome() {}
	@FXML
	private void ShowEmployees() {
		CustomersOverviewController customersOverviewController = new CustomersOverviewController(orderController, userController, sc);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CustomersOverview.fxml"));
		ChangeStage.change(this, loader, customersOverviewController, "Customer Overview");
	}
	@FXML
	private void showOrders() {}
}
