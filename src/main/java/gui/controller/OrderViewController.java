package gui.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import domain.OrderController;
import domain.CarrierController;
import domain.UserController;
import exceptions.EntityDoesntExistException;
import exceptions.OrderStatusException;
import gui.view.OrderDTO;
import gui.view.ProductDTO;
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

public class OrderViewController extends GridPane {

    @FXML
    private Label lblUser;
    @FXML
    private TableView<OrderDTO> TableOrdersView;
    @FXML
    private TableColumn<OrderDTO, Number> NumberColumnTable;
    @FXML
    private TableColumn<OrderDTO, LocalDate> DateColumnTable;
    @FXML
    private TableColumn<OrderDTO, String> CompanyColumnTable;
    @FXML
    private TableColumn<OrderDTO, String> StatusColumnTable;


    @FXML
    private TableView<OrderDTO> OrderDetailsTable;
    @FXML
    private TableColumn<OrderDTO, Number> IdColumnOrderTable;

    @FXML
    private TableColumn<OrderDTO, String> StatusColumnOrderTable;
    @FXML
    private TableColumn<OrderDTO, String> PackagingColumnOrderTable;
    @FXML
    private TableColumn<OrderDTO, String> carrierColumnOrderTable;
    @FXML
    private TableColumn<OrderDTO, String> TrackingColumnOrderTable;
    @FXML
    private TableColumn<OrderDTO, BigDecimal> TotalPriceColumnOrderTable;
    @FXML
    private TableView<ProductDTO> ProductsTableView;
    @FXML
    private TableColumn<ProductDTO, String> ProductColumnTable;
    @FXML
    private TableColumn<ProductDTO, Number> AmountColumnTable;
    @FXML
    private TableColumn<ProductDTO, Number> UnitPriceColumnTable;
    @FXML
    private TableColumn<ProductDTO, BigDecimal> TotalPriceColumnTable;
    @FXML
    private ListView<String> CustomerDetailsList;

    @FXML
    private ChoiceBox<String> choiceBoxCarriers;
    @FXML
    private Pane PaneOrderProcess;
    @FXML
    private Label lblSelectCarriers;
    @FXML
    private Button btnProcessOrder;

    @FXML
    private CheckBox OnlyPostedOrdersCheckBox;

    private final OrderController orderController;
    private final UserController userController;
    private final CarrierController carrierController;
    private ObservableList<OrderDTO> orderList;

    public OrderViewController(OrderController orderController, UserController userController,
                               CarrierController carrierController) {
        this.orderController = orderController;
        this.userController = userController;
        this.carrierController = carrierController;
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
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
        carrierColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().carrierProperty());
        TrackingColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().trackingCodeProperty());
        TotalPriceColumnOrderTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        //fill Products table of specified order
        ProductColumnTable.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        UnitPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty());
        AmountColumnTable.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        TotalPriceColumnTable.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        OnlyPostedOrdersCheckBox.selectedProperty().addListener(((observableValue, inactive, t1) -> {
            orderList = orderController.getOrderList(userController.userId(), !inactive);
            TableOrdersView.setItems(orderList);
        }
        ));

        orderList = orderController.getOrderList(userController.userId(), false);
        TableOrdersView.setItems(orderList);

        // Carriers
        ObservableList<String> carrierNames = carrierController.getCarrierNames(userController.supplierIdFromUser());

        if (carrierNames.size() == 0)
            showAlert("Error", "There are no active Carriers at the moment." + lineSeparator() + "Please try again later.", AlertType.ERROR);

        choiceBoxCarriers.setItems(carrierNames);
        choiceBoxCarriers.setValue(carrierNames.get(0));

        // Orders
        TableOrdersView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            PaneOrderProcess.setVisible(false);
            if (newOrder == null)
                return;

            int orderId = newOrder.getOrderId();
            OrderDetailsTable.setItems(orderController.getOrderByIdView(orderId));

            if (orderController.getOrderById(orderId).getCarrier() == null)
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
        String selectionCarrier = choiceBoxCarriers.getSelectionModel().getSelectedItem();
        int orderId = TableOrdersView.getSelectionModel().getSelectedItem().getOrderId();
        try {
            orderController.processOrder(orderId, selectionCarrier, userController.supplierIdFromUser());
            showAlert("Successful", "Order has been processed.", AlertType.INFORMATION);
        } catch (EntityNotFoundException | OrderStatusException | EntityDoesntExistException exception) {
            showAlert("Error", exception.getMessage(), AlertType.ERROR);
        }
        reselectProcessedOrder(orderId);
    }

    private void reselectProcessedOrder(int orderId) {
        for (OrderDTO orderDTO : TableOrdersView.getItems())
            if (orderDTO.getOrderId() == orderId) {
                TableOrdersView.getSelectionModel().select(orderDTO);
                break;
            }
    }

    @FXML
    public void showCustomers() {
        FXStageUtil.setScene(OrderViewController.class.getResource("/gui/CustomerView.fxml"), "Customers");
    }

    @FXML
    private void logOut() {
        FXStageUtil.setScene(OrderViewController.class.getResource("/gui/LoginView.fxml"), "Log In");
    }

}