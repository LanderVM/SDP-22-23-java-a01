package gui.controller;

import domain.*;
import gui.view.OrderView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.TableColumn;

public class OrdersOverviewController extends AnchorPane {
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
    private TableView<OrderView> TableOrdersView;
    @FXML
    private TableColumn<OrderView, Number> NumberColumnTable;
    @FXML
    private TableColumn<OrderView, Date> DateColumnTable;
    @FXML
    private TableColumn<OrderView, Double> CostColumnTable;
    @FXML
    private TableColumn<OrderView, String> OverviewColumnTable;
    @FXML
    private TableColumn<OrderView, Status> StatusColumnTable;
    @FXML
    private Label lblOrderOverview;

    private OrderController orderController;
    private UserController userController;
    private OrderView orderView;


    public OrdersOverviewController(OrderController orderController, UserController userController) {

        this.orderController = orderController;
        this.userController = userController;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/OrdersOverview.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }

        OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().getCompanyForTable());
        NumberColumnTable.setCellValueFactory(celldata -> celldata.getValue().idForTableProperty());
        DateColumnTable.setCellValueFactory(celldata -> celldata.getValue().dateForTableProperty());
        CostColumnTable.setCellValueFactory(celldata -> celldata.getValue().costForTableProperty());
        StatusColumnTable.setCellValueFactory(celldata -> celldata.getValue().statusForTableProperty());

        List<Order> ordersList = orderController.getOrderList();

        List<OrderView> viewList = new ArrayList<>();

        ordersList.forEach(order -> viewList.add(new OrderView(order)));

        System.out.println();
//        TableOrdersView.setItems(FXCollections.observableArrayList(viewList));
        TableOrdersView.setItems(FXCollections.observableArrayList(new OrderView(new Order("Tim CO", "Tim", "tim@mail.com", "Timlaan 24 1000 Brussel", new Date(), List.of(), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM)), new OrderView(new Order("Jan INC", "Jan", "jan@mail.com", "Janstraat 12 9000 Aalst", new Date(), List.of(), Status.POSTED, TransportService.BPOST, Packaging.CUSTOM))));


    }

    @FXML
    public void showOrders(ActionEvent event) {

    }

    // Event Listener on Hyperlink[#btnHome].onAction
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
		HomeWarehouseOperatorController homeWarehouseOperatorController = new HomeWarehouseOperatorController(orderController, userController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeWarehouseOperator.fxml"));
		changeStage(loader, homeWarehouseOperatorController);
	}

	private void goToHomeAdmin() {
		HomeAdminController homeAdminController = new HomeAdminController(orderController, userController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeAdmin.fxml"));
		changeStage(loader, homeAdminController);
	}

	private void changeStage(FXMLLoader loader, Parent controller) {
		loader.setRoot(controller);
		loader.setController(controller);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(controller);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Home");
		stage.show();
	}
}