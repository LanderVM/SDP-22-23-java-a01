package gui.controller;

import java.util.Date;
import java.util.List;

import domain.Order;
import domain.OrderController;
import domain.Packaging;
import domain.Status;
import domain.TransportService;
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

        idCol.setCellValueFactory(cellData->cellData.getValue().idForTableProperty());
        companyCol.setCellValueFactory(cellData->cellData.getValue().companyForTableProperty());
       dateCol.setCellValueFactory(cellData->cellData.getValue().dateForTableProperty());

        //processableOrdersTable.setItems(FXCollections.observableArrayList(orderController.getOrderList())); // TODO
       processableOrdersTable.setItems(FXCollections.observableArrayList(new OrderView(new Order("Tim CO", "Tim", "tim@mail.com", "Timlaan 24 1000 Brussel", new Date(), List.of(), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM)), new OrderView(new Order("Jan INC", "Jan", "jan@mail.com", "Janstraat 12 9000 Aalst", new Date(), List.of(), Status.POSTED, TransportService.BPOST, Packaging.CUSTOM))));

        processableOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            int id = newOrder.getIdForTable();
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
}
