package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.math.BigDecimal;
import java.util.Date;

import domain.OrderController;
import domain.Packaging;
import domain.Status;
import domain.SupplierController;
import domain.TransportService;
import domain.TransportServiceController;
import domain.UserController;
import gui.view.OrderView;
import gui.view.ProductView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ListView;

import javafx.scene.image.ImageView;

import javafx.scene.control.TableView;

import javafx.scene.layout.GridPane;
import util.FXStageUtil;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.TableColumn;

public class SingleOrderOverviewController extends GridPane {
	@FXML
	private GridPane MainPane;
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
	private GridPane HeaderPane;
	@FXML
	private ImageView imgDelaware;
	@FXML
	private Hyperlink btnHome;
	@FXML
	private Hyperlink btnNotifications;
	@FXML
	private Hyperlink btnOrders;
	@FXML
	private Label LblUser;
	@FXML
	private TableView<OrderView> OrderDetailsTable;
	@FXML
	private TableColumn<OrderView, Number> IdColumnOrderTable;
	@FXML
	private TableColumn<OrderView, String> CompanyColumnOrderTable;
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

	private final OrderController oc;
	private UserController uc;
	private TransportServiceController tsc;
	private SupplierController sc;

	public SingleOrderOverviewController(OrderController oc, UserController uc,
			TransportServiceController tsc, SupplierController sc) {
		this.oc = oc;
		this.uc = uc;
		this.sc = sc;
		this.tsc = tsc;	
	}
	
	@FXML
	private void initialize() {
		LblUser.setText(uc.toString());
		OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
		NumberColumnTable.setCellValueFactory(celldata -> celldata.getValue().orderIdProperty());
		DateColumnTable.setCellValueFactory(celldata -> celldata.getValue().dateProperty());
		StatusColumnTable.setCellValueFactory(celldata -> celldata.getValue().statusProperty());

        
        IdColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        CompanyColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        StatusColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        PackagingColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().packagingProperty());
        TransportServiceColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().transportServiceProperty());
        TrackingColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().trackingCodeProperty());
        TotalPriceColumnOrderTable.setCellValueFactory(cellData-> cellData.getValue().totalPriceProperty());
        
        ProductColumnTable.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        UnitPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().untitPriceProperty());
        AmountColumnTable.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        TotalPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        
        refreshOrderList();
        
        TableOrdersView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldCostumer, newCustomer) -> {
        	int id = TableOrdersView.getSelectionModel().getSelectedItem().getOrderId();
        	OrderDetailsTable.setItems(FXCollections.observableArrayList(oc.getOrderByIdView(id)));
        
        	setCustomerInfo();
        	ProductsTableView.setItems(FXCollections.observableArrayList(oc.getProductsList(id)));
        });
        

	}
	
	public void refreshOrderList() {
		TableOrdersView.setItems(FXCollections.observableArrayList(oc.getOrderList()));
    }
	
	public void setCustomerInfo() {
		CustomerDetailsList.getItems().clear();
		CustomerDetailsList.getItems().add("Customer Info: ");
		CustomerDetailsList.getItems().add(sc.getSupplierByName(CompanyColumnOrderTable.getCellData(0)).getName());
		CustomerDetailsList.getItems().add(sc.getSupplierByName(CompanyColumnOrderTable.getCellData(0)).getEmail());
		CustomerDetailsList.getItems().add(sc.getSupplierByName(CompanyColumnOrderTable.getCellData(0)).getAddress());
		CustomerDetailsList.getItems().add(String.format("PhoneNumber: %s", sc.getSupplierByName(CompanyColumnOrderTable.getCellData(0)).getPhoneNumber()));
	}
	
	@FXML
	public void showHome(ActionEvent event) {	
	}
	
	@FXML
	public void showNotifications(ActionEvent event) {		
	}
	
	// Event Listener on Hyperlink[#btnOrders].onAction
	@FXML
	public void showOrders(ActionEvent event) {
		OrdersOverviewController ordersOverviewController = new OrdersOverviewController(oc, uc, tsc, sc);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrdersOverview.fxml"));
		FXStageUtil.change(this, loader, ordersOverviewController, "Overview");
	}
}