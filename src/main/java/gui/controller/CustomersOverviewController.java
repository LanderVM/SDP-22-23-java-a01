package gui.controller;

import domain.Order;
import domain.OrderController;
import domain.Supplier;
import domain.SupplierController;
import domain.UserController;
import gui.view.CustomerVieuw;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class CustomersOverviewController extends GridPane{

	@FXML
    private ImageView imgDelaware;
	
	@FXML
    private Label lblUser;
	
    @FXML
    private Hyperlink homeBtn;
    
    @FXML
    private Hyperlink ordersBtn;

    @FXML
    private Label lblCustomerAdress;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerPhoneNumber;

    @FXML
    private ImageView logoImgView;
    
    @FXML
    private TableView<CustomerVieuw> CustomersOverviewTable;
    
    @FXML
    private TableColumn<CustomerVieuw, String> nameCustomerCol;
    
    @FXML
    private TableColumn<CustomerVieuw, Number> numberOfOrdersCol;

    @FXML
    private TableView<Order> ordersOfCustomerOverviewTable;
    
    @FXML
    private TableColumn<Order, Number> idOrderOfCustomerCol;

    @FXML
    private TableColumn<Order, String> dateOrderOfCustomerCol;
    
    @FXML
    private TableColumn<Order, String> statusOrderOfCustomerCol;
    
    private SupplierController sc;
	private UserController userController;
	private OrderController orderController;
    
    public CustomersOverviewController(SupplierController sc) {
    	this.sc = sc;
    }

	public CustomersOverviewController(OrderController orderController, UserController userController, SupplierController sc) {
		this.userController = userController;
		this.orderController = orderController;
		this.sc = sc;
	}
	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
			
		nameCustomerCol.setCellValueFactory(cellData -> cellData.getValue().getName());
		numberOfOrdersCol.setCellValueFactory(cellData -> cellData.getValue().getNumberOfOrders());
		
		refreshCustomersList();
		
		CustomersOverviewTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldCostumer, newCustomer) -> {
			
			String email = CustomersOverviewTable.getSelectionModel().getSelectedItem().getEmail().get();
			
			lblCustomerName.setText(sc.getSupplier(email).getName());
			lblCustomerAdress.setText(sc.getSupplier(email).getAddress());
			lblCustomerPhoneNumber.setText(Integer.toString(sc.getSupplier(email).getPhoneNumber()));
			
			nameCustomerCol.setCellValueFactory(cellData -> cellData.getValue().getName());
			numberOfOrdersCol.setCellValueFactory(cellData -> cellData.getValue().getNumberOfOrders());
		});
	}

	@FXML
    void showHome(ActionEvent event) {

    }

    @FXML
    void showNotifications(ActionEvent event) {

    }

    @FXML
    void showOrders(ActionEvent event) {
		TransportServicesOverviewController transportServicesOverviewController = new TransportServicesOverviewController(orderController, userController, sc);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TransportServicesOverview.fxml"));
		ChangeStage.change(this, loader, transportServicesOverviewController, "Overview");
    }
    
    public void refreshCustomersList() {
        CustomersOverviewTable.setItems(FXCollections.observableArrayList(sc.getSuppliersView()));
    }

}
