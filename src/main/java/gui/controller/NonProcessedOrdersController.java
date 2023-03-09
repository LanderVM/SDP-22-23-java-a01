package gui.controller;

import java.util.Date;

import domain.OrderController;
import domain.TransportServiceController;
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

public class NonProcessedOrdersController extends GridPane {


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
    private final TransportServiceController transportServiceController;
    private OrderView orderView;


    public NonProcessedOrdersController(OrderController orderController, UserController uc, TransportServiceController transportServiceController) {
        this.orderController = orderController;
        this.uc = uc;
        this.transportServiceController = transportServiceController;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/NonProcessedOrders.fxml"));
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
            ProcessOrderScreenController processOrderScreenController = new ProcessOrderScreenController(this.orderController, uc, transportServiceController, this, id);
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
    	/*OrdersOverviewController ordersOverviewController = new OrdersOverviewController(orderController, uc);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrdersOverview.fxml"));
		loader.setRoot(ordersOverviewController);
		loader.setController(ordersOverviewController);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(ordersOverviewController);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Orders Overview");
		stage.show();*/

        AllOrdersOverviewController ordersOverviewController = new AllOrdersOverviewController(orderController, uc, transportServiceController);
        Scene scene = new Scene(ordersOverviewController);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void refreshOrderList() {
        processableOrdersTable.setItems(FXCollections.observableArrayList(orderController.getPostedOrdersList()));
    }
}
