package gui.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

import domain.OrderController;
import domain.Packaging;
import domain.Status;
import domain.SupplierController;
import domain.TransportService;
import domain.TransportServiceController;
import domain.UserController;
import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.ProductView;
import gui.view.TransportServiceView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import util.FXStageUtil;

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
	private TableColumn<OrderView, String> CompanyColumnTable;
	@FXML
	private TableColumn<OrderView, Status> StatusColumnTable;
	
	
	@FXML
	private TableView<OrderView> OrderDetailsTable;
	@FXML
	private TableColumn<OrderView, Number> IdColumnOrderTable;

	@FXML
	private TableColumn<OrderView, Status> StatusColumnOrderTable;
	@FXML
	private TableColumn<OrderView, Packaging> PackagingColumnOrderTable;
	@FXML
	private TableColumn<OrderView, String> TransportServiceColumnOrderTable;
	@FXML
	private TableColumn<OrderView, Number> TrackingColumnOrderTable;
	@FXML
	private TableColumn<OrderView, BigDecimal> TotalPriceColumnOrderTable;
	@FXML
	private TableView<ProductView> ProductsTableView;
	@FXML
	private TableColumn<ProductView, String> ProductColumnTable;
	@FXML
	private TableColumn<ProductView, Number> AmountColumnTable;
	@FXML
	private TableColumn<ProductView, Number> UnitPriceColumnTable;
	@FXML
	private TableColumn<ProductView, BigDecimal> TotalPriceColumnTable;
	@FXML
	private ListView<String> CustomerDetailsList;
	
	@FXML
	private ChoiceBox<String> choiceBoxTransportServices;
	@FXML
	private Pane PaneOrderProcess;
	@FXML
	private Button btnProcessOrder;

	private final OrderController orderController;
	private final UserController userController;
	private final TransportServiceController transportServiceController;
	private final SupplierController supplierController;

	public OrdersOverviewController(OrderController orderController, UserController userController,
			TransportServiceController transportServiceController, SupplierController supplierController) {
		this.orderController = orderController;
		this.userController = userController;
		this.transportServiceController = transportServiceController;
		this.supplierController = supplierController;
	}

	@FXML
	private void initialize() {
		LblUser.setText(userController.toString());
		
		//Fill list of all orders
		NumberColumnTable.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
		CompanyColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
		DateColumnTable.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		StatusColumnTable.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		
		//fill orderDetails table
		IdColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        StatusColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        PackagingColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().packagingProperty());
        TransportServiceColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().transportServiceProperty());
        TrackingColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().trackingCodeProperty());
        TotalPriceColumnOrderTable.setCellValueFactory(cellData-> cellData.getValue().totalPriceProperty());
        
        //fill Products table of specified order
        ProductColumnTable.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        UnitPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().untitPriceProperty());
        AmountColumnTable.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        TotalPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        refreshOrderList();

		ObservableList<String> transportServiceNames = transportServiceController.getTransportServices()
		        .stream()
		        .map(TransportServiceView::getName)
		        .collect(Collectors.toCollection(FXCollections::observableArrayList));
		choiceBoxTransportServices.setItems(transportServiceNames);
		choiceBoxTransportServices.setValue(transportServiceNames.get(0));



        TableOrdersView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            PaneOrderProcess.setVisible(false);
        	if (newOrder == null) {
            	return;
            }
            
            int id = newOrder.getOrderId();
            OrderDetailsTable.setItems(FXCollections.observableArrayList(orderController.getOrderByIdView(id)));
            
            if(orderController.getOrderById(id).getTransportService()== null) {
            	PaneOrderProcess.setVisible(true);
            }
        	setCustomerInfo(id);
        	ProductsTableView.setItems(FXCollections.observableArrayList(orderController.getProductsList(id)));
        	
	
        });
       
		
	

		
	}

	private void setCustomerInfo(int id) {
		CustomerDetailsList.getItems().clear();	
		CustomerDetailsList.getItems().add(orderController.getOrderById(id).getSupplier().getName());
		CustomerDetailsList.getItems().add(orderController.getOrderById(id).getSupplier().getEmail());
		CustomerDetailsList.getItems().add(orderController.getOrderById(id).getSupplier().getAddress());
		CustomerDetailsList.getItems().add(orderController.getOrderById(id).getSupplier().getPhoneNumber());
		CustomerDetailsList.getItems().add(orderController.getOrderById(id).getDate().toString());	
	}

	@FXML
	public void showOrders(ActionEvent event) {
	}

    public void refreshOrderList() {
		

		TableOrdersView.setItems(FXCollections.observableArrayList(orderController.getOrderList()));
    }


	@FXML
	public void showHome(ActionEvent event) {
		
	}
	@FXML
	private void ProcessOrder(ActionEvent event) {
		  String selectionTransportService = choiceBoxTransportServices.getSelectionModel().getSelectedItem();
		  int id = TableOrdersView.getSelectionModel().getSelectedItem().getOrderId();
	        try {
				orderController.processOrder(id, transportServiceController.getTransportServiceByName(selectionTransportService));
			} catch (EntityNotFoundException | OrderStatusException e) {
				e.printStackTrace();
			}
	        refreshOrderList();
			TableOrdersView.getSelectionModel().select(0);
	}
	

	@FXML
	public void showNotifications(ActionEvent event) {
	}

	
}