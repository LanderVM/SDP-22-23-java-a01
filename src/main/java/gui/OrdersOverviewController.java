package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

import domain.DomainController;
import domain.Order;
import domain.Status;
import domain.UserController;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.TableColumn;

public class OrdersOverviewController extends AnchorPane{
	@FXML
	private AnchorPane MenuPane;
	@FXML
	private Hyperlink btnSupport;
	@FXML
	private Hyperlink btnHome;
	@FXML
	private Hyperlink btnOrders;
	@FXML
	private Hyperlink btnNotifications;
	@FXML
	private AnchorPane HeaderPane;
	@FXML
	private Label LblLogo;
	@FXML
	private Label LblUser;
	@FXML
	private AnchorPane PathPane;
	@FXML
	private Label lblFirstPath;
	@FXML
	private Label LabelNextPage;
	@FXML
	private Label LblSecondPath;
	@FXML
	private Line LineOrderOverview;
	@FXML
	private TableView<Order> TableOrdersView;
	@FXML
	private TableColumn<Order, Number> NumberColumnTable;
	@FXML
	private TableColumn<Order, Date> DateColumnTable;
	@FXML
	private TableColumn<Order, Double> CostColumnTable;
	@FXML
	private TableColumn<Order, String> OverviewColumnTable;
	@FXML
	private TableColumn<Order, Status> StatusColumnTable;
	@FXML
	private Label lblOrderOverview;

	private DomainController dc;
	private UserController uc;
	private Order or;
	
	
	
	public OrdersOverviewController(DomainController dc, UserController uc) {
		
		this.dc=dc;
		this.uc= uc;
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/OrdersOverview.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
        	loader.load();
        } catch (Exception e) {
			System.out.println(e);
		}
        OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyForTableProperty());
        NumberColumnTable.setCellValueFactory(celldata -> celldata.getValue().idForTableProperty());
        DateColumnTable.setCellValueFactory(celldata-> celldata.getValue().dateForTableProperty());
        CostColumnTable.setCellValueFactory(celldata -> celldata.getValue().costForTableProperty());
        StatusColumnTable.setCellValueFactory(celldata -> celldata.getValue().statusForTableProperty());
        
        System.out.println(dc.getObservableOrdersList());
        TableOrdersView.setItems(dc.getObservableOrdersList());
        
        
        
        
        
        
	}
	
	@FXML 
	public void showOrders(ActionEvent event) {
		
	}
	// Event Listener on Hyperlink[#btnHome].onAction
	@FXML
	public void showHome(ActionEvent event) {
		if (uc.userIsAdmin()) {
            goToAdminScreen();
        } else {
            goToWarehousmanScreen();
        }
	}
	
	
	@FXML
	public void showNotifications(ActionEvent event) {
		
	}
	
	private void goToWarehousmanScreen() {
		WarehousemanOverviewScreenController warehousemanOverviewScreenController = new WarehousemanOverviewScreenController(dc, uc);
        Scene scene = new Scene(warehousemanOverviewScreenController,600,600);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
		
	}
	private void goToAdminScreen() {
        HomeAdminController homeAdminController = new HomeAdminController(dc);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeAdmin.fxml"));
        loader.setRoot(homeAdminController);
        loader.setController(homeAdminController);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(homeAdminController, 600, 300);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }
}
