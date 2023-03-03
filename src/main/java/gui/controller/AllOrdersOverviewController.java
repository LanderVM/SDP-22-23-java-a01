package gui.controller;

import domain.*;
import gui.view.OrderView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
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

public class AllOrdersOverviewController extends AnchorPane {
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
    private TableColumn<OrderView, Double> CostColumnTable;
    @FXML
    private TableColumn<OrderView, String> OverviewColumnTable;
    @FXML
    private TableColumn<OrderView, Status> StatusColumnTable;
    @FXML
    private Label lblOrderOverview;

    private OrderController orderController;
    private UserController userController;
    List<Order> ordersList;


    public AllOrdersOverviewController(OrderController orderController, UserController userController) {

        this.orderController = orderController;
        this.userController = userController;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/AllOrdersOverview.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }

        OverviewColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        NumberColumnTable.setCellValueFactory(celldata -> celldata.getValue().orderIdProperty());
        DateColumnTable.setCellValueFactory(celldata -> celldata.getValue().dateProperty());
        CostColumnTable.setCellValueFactory(celldata -> celldata.getValue().totalPriceProperty());
        StatusColumnTable.setCellValueFactory(celldata -> celldata.getValue().statusProperty());

        ordersList = orderController.getOrderList();
        List<OrderView> viewList = ordersList.stream().map(OrderView::new).toList();
        TableOrdersView.setItems(FXCollections.observableArrayList(viewList));
    }

    @FXML
    public void showOrders(ActionEvent event) {
        NonProcessedOrdersController ordersOverviewController = new NonProcessedOrdersController(orderController, userController);
        Scene scene = new Scene(ordersOverviewController);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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