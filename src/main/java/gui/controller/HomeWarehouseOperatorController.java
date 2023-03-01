package gui.controller;

import java.io.IOException;

import domain.OrderController;
import domain.UserController;
import gui.controller.OrdersOverviewController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeWarehouseOperatorController extends AnchorPane {

    @FXML
    private Button btnViewCustomers;
    @FXML
    private Button btnViewOrders;
    @FXML
    private Button btnChangeTrackAndTrace;

    private OrderController oc;
    private UserController uc;

    public HomeWarehouseOperatorController(OrderController oc, UserController uc) {
        this.oc = oc;
        this.uc = uc;
    }

    @FXML
    void viewCustomers() {
        // TODO
    }

    @FXML
    void viewOrders() {
        OrdersOverviewController ordersOverviewController = new OrdersOverviewController(oc, uc);
        Scene scene = new Scene(ordersOverviewController);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changeTrackAndTrace() {
        //C
    }
}
