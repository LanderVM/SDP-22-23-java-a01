package gui.controller;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

public class OrdersOverviewController extends GridPane {
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
	private TableColumn<OrderView, String> OverviewColumnTable;
	@FXML
	private TableColumn<OrderView, Status> StatusColumnTable;
	@FXML
	private Label lblOrderOverview;
	@FXML
	private TextArea orderTextArea;
	@FXML
	private ChoiceBox<String> choiceBoxTransportServices;
	@FXML
	private TableView<OrderView> processableOrdersTable;

	private OrderController orderController;
	private UserController userController;
	private TransportServiceController transportServiceController;
	private int id = 1;
	List<Order> ordersList;

	public OrdersOverviewController(OrderController orderController, UserController userController,
			TransportServiceController transportServiceController) {
		this.orderController = orderController;
		this.userController = userController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	private void initialize() {
		LblUser.setText(userController.toString());
		OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
		NumberColumnTable.setCellValueFactory(celldata -> celldata.getValue().orderIdProperty());
		DateColumnTable.setCellValueFactory(celldata -> celldata.getValue().dateProperty());
		// CostColumnTable.setCellValueFactory(celldata ->
		// celldata.getValue().totalPriceProperty());
		StatusColumnTable.setCellValueFactory(celldata -> celldata.getValue().statusProperty());

		TableOrdersView.setItems(FXCollections.observableArrayList(orderController.getOrderList()));
		processableOrdersTable.setItems(FXCollections.observableArrayList(orderController.getPostedOrdersList()));
	}

	@FXML
	public void showOrders(ActionEvent event) {
	}

	@FXML
	private void klikked(MouseEvent event) {
		processableOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            if (newOrder == null)
                return;
            int id = newOrder.getOrderId();
            System.out.println("test");
		});
		processableOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
        	if (newOrder == null) {return;}
        		id = newOrder.getOrderId();
                orderTextArea.setText(orderController.getOrderOverview(id));
                choiceBoxTransportServices.setItems(FXCollections.observableArrayList(transportServiceController.getTransportServices()));
        });
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
		HomeWarehouseOperatorController homeWarehouseOperatorController = new HomeWarehouseOperatorController(
				orderController, userController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeWarehouseOperator.fxml"));
		ChangeStage.change(this, loader, homeWarehouseOperatorController, "Home");
	}

	private void goToHomeAdmin() {
		HomeAdminController homeAdminController = new HomeAdminController(orderController, userController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeAdmin.fxml"));
		ChangeStage.change(this, loader, homeAdminController, "Home");
	}
}