package gui.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

import domain.OrderController;
import domain.Status;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import gui.view.ContactPersonSupplierView;
import gui.view.CustomerOrdersView;
import gui.view.CustomerView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import util.FXStageUtil;

public class CustomersOverviewController extends GridPane {

	@FXML
	private ImageView imgDelaware;

	@FXML
	private Label lblUser;
	
	@FXML
	private GridPane GridPaneCustomerInfo;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Hyperlink btnOrders;

	@FXML
	private Hyperlink btnCustomers;

	@FXML
	private Label lblCustomerAdress;

	@FXML
	private Label lblCustomerName;

	@FXML
	private Label lblCustomerPhoneNumber;

	@FXML
	private ImageView logoImgView;

	@FXML
	private TableView<CustomerView> CustomersOverviewTable;

	@FXML
	private TableColumn<CustomerView, String> nameCustomerCol;

	@FXML
	private TableColumn<CustomerView, Number> numberOfOrdersCol;

	@FXML
	private TableView<CustomerOrdersView> ordersOfCustomerOverviewTable;

	@FXML
	private TableColumn<CustomerOrdersView, Number> idOrderOfCustomerCol;

	@FXML
	private TableColumn<CustomerOrdersView, LocalDate> dateOrderOfCustomerCol;

	@FXML
	private TableColumn<CustomerOrdersView, Status> statusOrderOfCustomerCol;

	@FXML
	private TableView<ContactPersonSupplierView> contactpesronSupplierOverviewTable;

	@FXML
	private TableColumn<ContactPersonSupplierView, String> nameContactpersonSupplierCol;

	@FXML
	private TableColumn<ContactPersonSupplierView, String> emailContactpersonSupplierCol;

	private final SupplierController supplierController;
	private final UserController userController;
	private final OrderController orderController;
	private final TransportServiceController transportServiceController;

	public CustomersOverviewController(OrderController orderController, UserController userController,TransportServiceController transportServiceController,
			SupplierController supplierController) {
		this.userController = userController;
		this.orderController = orderController;
		this.supplierController = supplierController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());

		nameCustomerCol.setCellValueFactory(cellData -> cellData.getValue().getName());
		numberOfOrdersCol.setCellValueFactory(cellData -> cellData.getValue().getNumberOfOrders());

		idOrderOfCustomerCol.setCellValueFactory(cellData -> cellData.getValue().getOrderPropertyId());
		dateOrderOfCustomerCol.setCellValueFactory(cellData -> cellData.getValue().getOrderPropertyDate());
		statusOrderOfCustomerCol.setCellValueFactory(cellData -> cellData.getValue().getOrderPropertyStatus());

		nameContactpersonSupplierCol.setCellValueFactory(cellData -> cellData.getValue().getName());
		emailContactpersonSupplierCol.setCellValueFactory(cellData -> cellData.getValue().getEmail());

		refreshCustomersList();

		CustomersOverviewTable.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldCostumer, newCustomer) -> {

					String email = CustomersOverviewTable.getSelectionModel().getSelectedItem().getEmail().get();

					lblCustomerName.setText(supplierController.getSupplier(email).getName());
					lblCustomerAdress.setText(supplierController.getSupplier(email).getAddress());
					lblCustomerPhoneNumber.setText(supplierController.getSupplier(email).getPhoneNumber());

					Image img = new Image(new ByteArrayInputStream(supplierController.getSupplier(email).getLogo()));
					logoImgView.setImage(img);

					ordersOfCustomerOverviewTable.getSortOrder().add(idOrderOfCustomerCol);
					ordersOfCustomerOverviewTable
							.setItems(supplierController.getCustomerOrderView(email));
					
					nameCustomerCol.setCellValueFactory(cellData -> cellData.getValue().getName());
					numberOfOrdersCol.setCellValueFactory(cellData -> cellData.getValue().getNumberOfOrders());

					contactpesronSupplierOverviewTable
							.setItems(supplierController.getContactPersonSupplierView(email));

					nameContactpersonSupplierCol.setCellValueFactory(cellData -> cellData.getValue().getName());
					emailContactpersonSupplierCol.setCellValueFactory(cellData -> cellData.getValue().getEmail());
				});
	}

	@FXML
	void showHome(ActionEvent event) {

	}

	@FXML
	void showCustomers(ActionEvent event) {

	}

	@FXML
	void showOrders(ActionEvent event) {
		OrdersOverviewController ordersOverviewController = new OrdersOverviewController(orderController, userController, transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrdersOverview.fxml"));
		FXStageUtil.change(loader, ordersOverviewController, "Overview");
	}

	public void refreshCustomersList() {
		CustomersOverviewTable
				.setItems(supplierController.getSuppliersView(userController.supplierIdFromUser()));
	}

}
