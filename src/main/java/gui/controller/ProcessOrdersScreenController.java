package gui.controller;

import java.util.Date;
import java.util.List;

import domain.Order;
import domain.OrderController;
import domain.UserController;
import gui.view.OrderView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProcessOrdersScreenController extends GridPane {


    @FXML
    private TableColumn<OrderView, String> companyCol;

    @FXML
    private TableColumn<OrderView, Date> dateCol;

    @FXML
    private TableColumn<OrderView, Number> idCol;

    @FXML
    private TableView<OrderView> processableOrdersTable;

    @FXML
    private Button backToOverviewButton;

    @FXML
    private TextField txtFilter;

    private OrderController orderController;
    private UserController uc;
    private OrderView orderView;
    private List<Order> ordersList;

    public ProcessOrdersScreenController(OrderController orderController, UserController uc) {
        this.orderController = orderController;
        this.uc = uc;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/ProcessOrdersScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }

        idCol.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        companyCol.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        refreshOrderList();

        processableOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            if (newOrder == null)
                return;
            int id = newOrder.getOrderId();
            ProcessOrderScreenController processOrderScreenController = new ProcessOrderScreenController(this.orderController, this, id);
            Scene scene = new Scene(processOrderScreenController);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        });
    }

//    @FXML
//    void filter(KeyEvent event) {
//    	String newValue = txtFilter.getText();
//    	dc.changeFilter(newValue);
//    } TODO

    @FXML
    void backToOverview(ActionEvent event) {
        //Stage stage = (Stage) this.getScene().getWindow();
        //stage.setScene(parent.getScene());
    }

    public void refreshOrderList() {
        ordersList = orderController.getOrderList();
        List<OrderView> viewList = ordersList.stream().map(OrderView::new).toList();
        processableOrdersTable.setItems(FXCollections.observableArrayList(viewList));
    }
}
