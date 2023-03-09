package gui.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import domain.Order;
import domain.OrderController;
import domain.Status;
import domain.TransportServiceController;
import domain.UserController;
import gui.view.OrderView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class AllOrdersOverviewController extends AnchorPane {
	@FXML
	private Hyperlink btnSupport;
	@FXML
	private Hyperlink btnHome;
	@FXML
	private Hyperlink btnOrders;
	@FXML
	private Label LblUser;
	@FXML
	private Line LineOrderOverview;
	@FXML
	private TableView<OrderView> TableOrdersView;
	@FXML
	private TableColumn<OrderView, Number> NumberColumnTable;
	@FXML
	private TableColumn<OrderView, Date> DateColumnTable;
	@FXML
	private TableColumn<OrderView, BigDecimal> CostColumnTable;
	@FXML
	private TableColumn<OrderView, String> OverviewColumnTable;
	@FXML
	private TableColumn<OrderView, Status> StatusColumnTable;
	@FXML
	private Label lblOrderOverview;

	private OrderController orderController;
	private UserController userController;
	private TransportServiceController transportServiceController;
	List<Order> ordersList;

	public AllOrdersOverviewController(OrderController orderController, UserController userController, TransportServiceController transportServiceController) {
		this.orderController = orderController;
		this.userController = userController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	private void initialize() {
		OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
		NumberColumnTable.setCellValueFactory(celldata -> celldata.getValue().orderIdProperty());
		DateColumnTable.setCellValueFactory(celldata -> celldata.getValue().dateProperty());
		CostColumnTable.setCellValueFactory(celldata -> celldata.getValue().totalPriceProperty());
		StatusColumnTable.setCellValueFactory(celldata -> celldata.getValue().statusProperty());

        TableOrdersView.setItems(FXCollections.observableArrayList(orderController.getOrderList()));
    }

	@FXML
	public void showOrders(ActionEvent event) {
		// Mag weg
		/*
		 * NonProcessedOrdersController ordersOverviewController = new
		 * NonProcessedOrdersController(orderController, userController); Scene scene =
		 * new Scene(ordersOverviewController); Stage stage = (Stage)
		 * this.getScene().getWindow(); stage.setScene(scene); stage.show();
		 */
	}

	@FXML
	public void showHome(ActionEvent event) {
		if (userController.userIsAdmin()) {
			goToHomeAdmin();
		} else {
			goToHomeWarehouseOperator();
		}
	}

	@FXML
	public void showNotifications(ActionEvent event) {

	}

	private void goToHomeWarehouseOperator() {
		HomeWarehouseOperatorController homeWarehouseOperatorController = new HomeWarehouseOperatorController(orderController, userController, transportServiceController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeWarehouseOperator.fxml"));
		ChangeStage.change(this, loader, homeWarehouseOperatorController, "Home");
	}

	private void goToHomeAdmin() {
		HomeAdminController homeAdminController = new HomeAdminController(orderController, userController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeAdmin.fxml"));
		ChangeStage.change(this, loader, homeAdminController, "Home");
	}
}