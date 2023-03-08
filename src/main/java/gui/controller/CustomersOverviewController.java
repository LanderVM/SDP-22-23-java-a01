package gui.controller;

import domain.Order;
import domain.Supplier;
import domain.SupplierController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class CustomersOverviewController {

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
    private TableView<Supplier> CustomersOverviewTable;
    
    @FXML
    private TableColumn<Supplier, String> nameCustomerCol;
    
    @FXML
    private TableColumn<Supplier, Number> numberOfOrdersCol;

    @FXML
    private TableView<Order> ordersOfCustomerOverviewTable;
    
    @FXML
    private TableColumn<Order, Number> idOrderOfCustomerCol;

    @FXML
    private TableColumn<Order, String> dateOrderOfCustomerCol;
    
    @FXML
    private TableColumn<Order, String> statusOrderOfCustomerCol;
    
    private SupplierController sc;
    
    public CustomersOverviewController (SupplierController sc) {
    	this.sc = sc;
    }
    
    

    @FXML
    void showHome(ActionEvent event) {

    }

    @FXML
    void showNotifications(ActionEvent event) {

    }

    @FXML
    void showOrders(ActionEvent event) {

    }

}
