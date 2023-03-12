package gui.controller;

import java.util.Date;
import java.util.List;

import domain.Order;
import domain.OrderController;
import domain.Status;
import domain.TransportService;
import domain.TransportServiceController;
import domain.User;
import domain.UserController;
import exceptions.OrderStatusException;
import gui.view.OrderView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
	@FXML
	private TableColumn<OrderView, Number> idCol;
	@FXML
	private TableColumn<OrderView, String> companyCol;
	@FXML
	private TableColumn<OrderView, Date> dateCol;
	@FXML
	private Button btnProcessOrder;

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
		NumberColumnTable.setCellValueFactory(celldata -> celldata.getValue().orderIdProperty());
		OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
		DateColumnTable.setCellValueFactory(celldata -> celldata.getValue().dateProperty());
		StatusColumnTable.setCellValueFactory(celldata -> celldata.getValue().statusProperty());

        choiceBoxTransportServices.setItems(FXCollections.observableArrayList(transportServiceController.getTransportServices()));
        
        idCol.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        companyCol.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        refreshOrderList();

		orderTextArea.setText(orderController.getOrderOverview(id));
		
        processableOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            if (newOrder == null) {
            	return;
            }
            int id = newOrder.getOrderId();
            TableOrdersView.requestFocus();
            TableOrdersView.getSelectionModel().select(id-1);
            TableOrdersView.getFocusModel().focus(id-1);
            orderTextArea.setText(orderController.getOrderOverview(id));
        });
        TableOrdersView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            if (newOrder == null)
                return;
            int id = newOrder.getOrderId();
            processableOrdersTable.requestFocus();
            processableOrdersTable.getSelectionModel().select(id-1);
            processableOrdersTable.getFocusModel().focus(id-1);
            orderTextArea.setText(orderController.getOrderOverview(id));
        });
	}

	@FXML
	public void showOrders(ActionEvent event) {
	}

    public void refreshOrderList() {
        processableOrdersTable.setItems(FXCollections.observableArrayList(orderController.getPostedOrdersListForSupplier(userController.userId())));
		TableOrdersView.setItems(FXCollections.observableArrayList(orderController.getOrderListForSupplier(userController.userId())));
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
	private void ProcessOrder(ActionEvent event) {
		  String selectionTransportService = choiceBoxTransportServices.getSelectionModel().getSelectedItem();
	        if (selectionTransportService == null) {
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Warning");
	            alert.setHeaderText("You need to select a transport service in order to be able to process order");
	            alert.showAndWait();
	            return;
	        }
	        try {
				orderController.processOrder(id, new TransportService(selectionTransportService, null, null, isCache()));
			} catch (EntityNotFoundException | OrderStatusException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        refreshOrderList();
	    	
	}
	

	@FXML
	public void showNotifications(ActionEvent event) {
	}

	private void goToHomeWarehouseOperator() {
		HomeWarehouseOperatorController homeWarehouseOperatorController = new HomeWarehouseOperatorController(
				orderController, userController, transportServiceController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeWarehouseOperator.fxml"));
		ChangeStage.change(this, loader, homeWarehouseOperatorController, "Home");
	}

	private void goToHomeAdmin() {
		HomeAdminController homeAdminController = new HomeAdminController(orderController, userController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeAdmin.fxml"));
		ChangeStage.change(this, loader, homeAdminController, "Home");
	}
}