package gui.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import domain.OrderController;
import domain.PackagingType;
import domain.Status;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.ProductView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import util.FXStageUtil;

public class OrdersOverviewController extends GridPane {
	
	@FXML 
	private Hyperlink btnCustomers;
	@FXML
	private Hyperlink btnHome;
	@FXML
	private Hyperlink btnOrders;
	@FXML
	private Label lblUser;
	@FXML
	private Line LineOrderOverview;
	@FXML
	private TableView<OrderView> TableOrdersView;
	@FXML
	private TableColumn<OrderView, Number> NumberColumnTable;
	@FXML
	private TableColumn<OrderView, LocalDate> DateColumnTable;
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
	private TableColumn<OrderView, PackagingType> PackagingColumnOrderTable;
	@FXML
	private TableColumn<OrderView, String> TransportServiceColumnOrderTable;
	@FXML
	private TableColumn<OrderView, String> TrackingColumnOrderTable;
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
	
	private Alert alert;

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
		lblUser.setText(userController.toString());
		
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
        TotalPriceColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        
        //fill Products table of specified order
        ProductColumnTable.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        UnitPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().untitPriceProperty());
        AmountColumnTable.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        TotalPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        refreshOrderList();

        // TransportServices
        ObservableList<String> transportServiceNames = transportServiceController.getTransportServicesNames(userController.supplierIdFromUser());
        
        if (transportServiceNames.size() == 0)
            alert = new Alert(AlertType.ERROR, "There are no active transport services at the moment.\nPlease try again later.", ButtonType.CLOSE);
        choiceBoxTransportServices.setItems(transportServiceNames);
        choiceBoxTransportServices.setValue(transportServiceNames.get(0));

        // Orders
        TableOrdersView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            PaneOrderProcess.setVisible(false);
            if (newOrder == null)
                return;

            int orderId = newOrder.getOrderId();
            OrderDetailsTable.setItems(orderController.getOrderByIdView(orderId));

            if (orderController.getOrderById(orderId).getTransportService() == null)
                PaneOrderProcess.setVisible(true);

            setCustomerInfo(orderId);
            ProductsTableView.setItems(orderController.getProductsList(orderId));
        });
    }

	private void setCustomerInfo(int orderId) {
		CustomerDetailsList.getItems().clear();	
		CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getName());
		CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getEmail());
		CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getAddress());
		CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getPhoneNumber());
		CustomerDetailsList.getItems().add(orderController.getOrderById(orderId).getDate().toString());	
	}

    @FXML
    public void showOrders(ActionEvent event) {
    }

    public void refreshOrderList() {
        TableOrdersView.setItems(orderController.getOrderListForUser(userController.userId()));
    }


    @FXML
    public void showHome(ActionEvent event) {
    }

    @FXML
    private void ProcessOrder(ActionEvent event) {
        String selectionTransportService = choiceBoxTransportServices.getSelectionModel().getSelectedItem();
        int orderId = TableOrdersView.getSelectionModel().getSelectedItem().getOrderId();
        try {
            
            
            orderController.processOrder(orderId, selectionTransportService,userController.supplierIdFromUser());
            Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Login Error");
    		alert.setHeaderText(null);
    		alert.setContentText("Succesfully processed the order!");
    		alert.showAndWait();
        } catch (EntityNotFoundException | OrderStatusException e) {
            e.printStackTrace();
        }
        refreshOrderList();
        reselectProcessedOrder(orderId);
    }
    
    private void reselectProcessedOrder (int orderId) {
    	int index = 0;
        for (int i = 0; i < TableOrdersView.getItems().size(); i++) {
            if (TableOrdersView.getItems().get(i).getOrderId()==orderId) {
                index = i;
                break;
            }
        }
        TableOrdersView.getSelectionModel().select(index);
    }

	@FXML
	public void showCustomers(ActionEvent event) {
		CustomersOverviewController customersOverviewController = new CustomersOverviewController(orderController, userController, transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CustomersOverview.fxml"));
		FXStageUtil.change(loader, customersOverviewController, "Customers");
	}

	@FXML
	private void logOut() {
		LoginScreenController loginScreenController = new LoginScreenController(orderController, userController, transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginScreen.fxml"));
		FXStageUtil.change(loader, loginScreenController, "Log In");
	}

}