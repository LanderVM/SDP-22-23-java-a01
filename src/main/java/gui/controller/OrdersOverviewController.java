package gui.controller;

import java.util.Date;
import java.util.stream.Collectors;

import domain.OrderController;
import domain.Status;
import domain.TransportService;
import domain.TransportServiceController;
import domain.UserController;
import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.TransportServiceView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

	private final OrderController orderController;
	private final UserController userController;
	private final TransportServiceController transportServiceController;

	public OrdersOverviewController(OrderController orderController, UserController userController,
			TransportServiceController transportServiceController) {
		this.orderController = orderController;
		this.userController = userController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	private void initialize() {
		LblUser.setText(userController.toString());
		
		NumberColumnTable.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
		OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
		DateColumnTable.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		StatusColumnTable.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		
		idCol.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
		companyCol.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        refreshOrderList();

		ObservableList<String> transportServiceNames = transportServiceController.getTransportServices()
		        .stream()
		        .map(TransportServiceView::getName)
		        .collect(Collectors.toCollection(FXCollections::observableArrayList));
		choiceBoxTransportServices.setItems(transportServiceNames);
		choiceBoxTransportServices.setValue(transportServiceNames.get(0));



        processableOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            if (newOrder == null) {
            	return;
            }
            btnProcessOrder.setDisable(false);
            int id = newOrder.getOrderId();
            TableOrdersView.requestFocus();
            orderTextArea.setText(orderController.getOrderOverview(id));
            TableOrdersView.getSelectionModel().clearSelection();
        });
        TableOrdersView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            if (newOrder == null)
                return;

            btnProcessOrder.setDisable(true);
            int id = newOrder.getOrderId();
            processableOrdersTable.requestFocus();
            orderTextArea.setText(orderController.getOrderOverview(id));
            processableOrdersTable.getSelectionModel().clearSelection();
        });

		TableOrdersView.getSelectionModel().select(0);
	}

	@FXML
	public void showOrders(ActionEvent event) {
	}

    public void refreshOrderList() {
		orderTextArea.setText("");
        processableOrdersTable.setItems(FXCollections.observableArrayList(orderController.getPostedOrdersList()));
		TableOrdersView.setItems(FXCollections.observableArrayList(orderController.getOrderList()));
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
		  int id = processableOrdersTable.getSelectionModel().getSelectedItem().getOrderId();
	        try {
				orderController.processOrder(id, new TransportService(selectionTransportService, null, null, isCache()));
			} catch (EntityNotFoundException | OrderStatusException e) {
				e.printStackTrace();
			}
	        refreshOrderList();
			TableOrdersView.getSelectionModel().select(0);
	}
	

	@FXML
	public void showNotifications(ActionEvent event) {
	}

	private void goToHomeWarehouseOperator() {
	}

	private void goToHomeAdmin() {
	}
}