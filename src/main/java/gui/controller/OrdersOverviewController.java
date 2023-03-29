package gui.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import domain.OrderController;
import domain.Status;
import domain.TransportServiceController;
import domain.UserController;
import exceptions.EntityDoesntExistException;
import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.ProductView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import util.FXStageUtil;

import static java.lang.System.lineSeparator;

public class OrdersOverviewController extends GridPane {

    @FXML
    private Label lblUser;
    @FXML
    private TableView<OrderView> TableOrdersView;
    @FXML
    private TableColumn<OrderView, Number> NumberColumnTable;
    @FXML
    private TableColumn<OrderView, LocalDate> DateColumnTable;
    @FXML
    private TableColumn<OrderView, String> CompanyColumnTable;
    @FXML
    private TableColumn<OrderView, Status> StatusColumnTable;


    @FXML
    private TableView<OrderView> OrderDetailsTable;
    @FXML
    private TableColumn<OrderView, Number> IdColumnOrderTable;

    @FXML
    private TableColumn<OrderView, Status> StatusColumnOrderTable;
    @FXML
    private TableColumn<OrderView, String> PackagingColumnOrderTable;
    @FXML
    private TableColumn<OrderView, String> TransportServiceColumnOrderTable;
    @FXML
    private TableColumn<OrderView, String> TrackingColumnOrderTable;
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

    @FXML
    private ChoiceBox<String> choiceBoxTransportServices;
    @FXML
    private Pane PaneOrderProcess;
    @FXML
    private Label lblSelectTransportService;
    @FXML
    private Button btnProcessOrder;

    @FXML
    private CheckBox OnlyPostedOrdersCheckBox;

    private Alert alert;

    private final OrderController orderController;
    private final UserController userController;
    private final TransportServiceController transportServiceController;
    private ObservableList<OrderView> orderList;

    public OrdersOverviewController(OrderController orderController, UserController userController,
                                    TransportServiceController transportServiceController) {
        this.orderController = orderController;
        this.userController = userController;
        this.transportServiceController = transportServiceController;
    }

    private void showAlert(String title, String message, AlertType alertType) {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        lblUser.setText(userController.toString());

        //Fill list of all orders
        NumberColumnTable.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        CompanyColumnTable.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        DateColumnTable.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        StatusColumnTable.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        //fill orderDetails table
        IdColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        StatusColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        PackagingColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().packagingProperty());
        TransportServiceColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().transportServiceProperty());
        TrackingColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().trackingCodeProperty());
        TotalPriceColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        //fill Products table of specified order
        ProductColumnTable.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        UnitPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().untitPriceProperty());
        AmountColumnTable.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        TotalPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        OnlyPostedOrdersCheckBox.selectedProperty().addListener(((observableValue, inactive, t1) -> {
            orderList = orderController.getOrderList(userController.userId(), !inactive);
            TableOrdersView.setItems(orderList);
        }
        ));

        orderList = orderController.getOrderList(userController.userId(), false);
        TableOrdersView.setItems(orderList);

        // TransportServices
        ObservableList<String> transportServiceNames = transportServiceController.getTransportServicesNames(userController.supplierIdFromUser());

        if (transportServiceNames.size() == 0)
            showAlert("Error", "There are no active Carriers at the moment." + lineSeparator() + "Please try again later.", AlertType.ERROR);

        choiceBoxTransportServices.setItems(transportServiceNames);
        choiceBoxTransportServices.setValue(transportServiceNames.get(0));

        // Orders
        TableOrdersView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            PaneOrderProcess.setVisible(false);
            if (newOrder == null)
                return;

            int orderId = newOrder.getOrderId();
            OrderDetailsTable.setItems(orderController.getOrderByIdView(orderId));

            if (orderController.getOrderById(orderId).getTransportService() == null)
                PaneOrderProcess.setVisible(true);

            setCustomerInfo(orderId);
            ProductsTableView.setItems(orderController.getProductsList(orderId));
        });
    }

    private void setCustomerInfo(int orderId) {
        CustomerDetailsList.getItems().clear();
        CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getName());
        CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getEmail());
        CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getAddress());
        CustomerDetailsList.getItems().add(orderController.getCustomerForOrder(orderId).getPhoneNumber());
        CustomerDetailsList.getItems().add(orderController.getOrderById(orderId).getDate().toString());
    }

    @FXML
    private void ProcessOrder() {
        String selectionTransportService = choiceBoxTransportServices.getSelectionModel().getSelectedItem();
        int orderId = TableOrdersView.getSelectionModel().getSelectedItem().getOrderId();
        try {
            orderController.processOrder(orderId, selectionTransportService, userController.supplierIdFromUser());
            showAlert("Successful", "Order has been processed.", AlertType.INFORMATION);
        } catch (EntityNotFoundException | OrderStatusException | EntityDoesntExistException exception) {
            showAlert("Error", exception.getMessage(), AlertType.ERROR);
        }
        reselectProcessedOrder(orderId);
    }

    private void reselectProcessedOrder(int orderId) {
        for (OrderView orderView : TableOrdersView.getItems())
            if (orderView.getOrderId() == orderId) {
                TableOrdersView.getSelectionModel().select(orderView);
                break;
            }
    }

    @FXML
    public void showCustomers() {
        FXStageUtil.setScene(OrdersOverviewController.class.getResource("/gui/CustomersOverview.fxml"), "Customers");
    }

    @FXML
    private void logOut() {
        FXStageUtil.setScene(OrdersOverviewController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
    }

}