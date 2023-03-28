package gui.controller;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import domain.Status;
import domain.SupplierController;
import domain.UserController;
import gui.view.ContactPersonSupplierView;
import gui.view.CustomerOrdersView;
import gui.view.CustomerView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import util.FXStageUtil;

public class CustomersOverviewController extends GridPane {

	@FXML
	public Label lblName;
	@FXML
	public Label lblAdress;
	@FXML
	public Label lblPhone;
	@FXML
	private Label lblUser;
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

	public CustomersOverviewController(UserController userController, SupplierController supplierController) {
		this.userController = userController;
		this.supplierController = supplierController;
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

		CustomersOverviewTable.setItems(supplierController.getSuppliersView(userController.supplierIdFromUser()));

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
	void showOrders() {
		FXStageUtil.setScene(CustomersOverviewController.class.getResource("/gui/OrdersOverview.fxml"), "Orders");
	}

	@FXML
	private void logOut() {
		FXStageUtil.setScene(CustomersOverviewController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
	}

}
