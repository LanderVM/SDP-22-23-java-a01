package gui.controller;

import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import gui.view.ContactPersonView;
import gui.view.TransportServiceView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class TransportServicesOverviewController extends GridPane {

	@FXML
	private Button btnSave;
	@FXML
	private Button btnAdd;
	@FXML 
	private Label lblUser;
	@FXML
	private TableView<TransportServiceView> tblTransportServices;
	@FXML
	private TableColumn<TransportServiceView, String> tblTransportservicesClmName;
	@FXML
	private TableColumn<TransportServiceView, String> tblTransportservicesClmStatus;
	@FXML
	private TableView<TransportServiceView> tblTransportServicesInfo;
	@FXML
	private TableColumn<TransportServiceView, String> tblTransportServicesInfoClmName;
	@FXML
	private TableColumn<TransportServiceView, Number> tblTransportServicesInfoClmCharAmount;
	@FXML
	private TableColumn<TransportServiceView, String> tblTransportServicesInfoClmPrefix;
	@FXML
	private TableView<ContactPersonView> tblContactPerson;
	@FXML
	private TableColumn<ContactPersonView, String> tblContactPersonClmPhone;
	@FXML
	private TableColumn<ContactPersonView, String> tblContactPersonClmEmail;
	@FXML
	private CheckBox chkboxIsActive;

	private OrderController orderController;
	private UserController userController;
	private SupplierController supplierController;
	private TransportServiceController transportServiceController;
	private int id = -1;
	
	public TransportServicesOverviewController(OrderController orderController, UserController userController, SupplierController supplierController, TransportServiceController transportServiceController) {
		this.orderController = orderController;
		this.userController = userController;
		this.supplierController = supplierController;
		this.transportServiceController = transportServiceController;
	}


	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
		
		//Table TransportService
		tblTransportservicesClmName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblTransportservicesClmStatus.setCellValueFactory(cellData -> cellData.getValue().activeProperty().get()==true?new SimpleStringProperty( "active"):new SimpleStringProperty("not active"));
		
		refreshCustomersList();
		
		tblTransportServices.getSelectionModel().selectedItemProperty()
		.addListener((observableValue, oldCostumer, newCustomer) -> {

			String name = newCustomer.getName();
			id = newCustomer.getTransportServiceId();
			
			//Table info TransportService
			tblTransportServicesInfoClmName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			tblTransportServicesInfoClmCharAmount.setCellValueFactory(cellData -> cellData.getValue().characterCountProperty());
			tblTransportServicesInfoClmPrefix.setCellValueFactory(cellData -> cellData.getValue().prefixProperty());

			//Table ContactPersonSupplier
			tblContactPersonClmPhone.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
			tblContactPersonClmEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

			tblTransportServicesInfo.setItems(FXCollections.observableArrayList(transportServiceController.getTransportServices().get(id)));
			//tblContactPerson.setItems(FXCollections.observableArrayList(supplierController.getContactPersonSupplierView(name)));
		});
	}
	public void refreshCustomersList() {
		tblTransportServices.setItems(transportServiceController.getTransportServices());
	}
	
	@FXML
	private void Save() {
		transportServiceController.setActive(id, chkboxIsActive.isSelected());
		refreshCustomersList();
	}

	@FXML
	private void Add() {
	}
	
	@FXML
	private void showHome() {}
	@FXML
	private void ShowEmployees() {
		/*
		CustomersOverviewController customersOverviewController = new CustomersOverviewController(orderController, userController, sc);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CustomersOverview.fxml"));
		FXStageUtil.change(this, loader, customersOverviewController, "Customer Overview");
		*/
		//FOUTE CODE ?!
	}
	@FXML
	private void showOrders() {}
}
