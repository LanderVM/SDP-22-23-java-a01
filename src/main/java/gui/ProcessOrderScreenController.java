package gui;

import domain.DomainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProcessOrderScreenController extends GridPane {

    @FXML
    private Button backToProcessOrdersScreen;

    @FXML
    private GridPane baseGrid;

    @FXML
    private ChoiceBox<String> transportServicesBox;

    @FXML
    private TextArea orderTxt;

    @FXML
    private Label overviewLbl;

    @FXML
    private Button processOrderBtn;

    private DomainController domainController;
    private final ProcessOrdersScreenController parent;
    private int id;


    public ProcessOrderScreenController(DomainController domainController, ProcessOrdersScreenController parent, int id) {
        this.domainController = domainController;
        this.parent = parent;


        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/ProcessOrderScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }

        overviewLbl.setText("Overview order: " + Integer.toString(id));
        orderTxt.setText(domainController.getOrderOverview(id));
        transportServicesBox.setItems(domainController.getTransportServicesList());
    }

    @FXML
    void backToProcessOrdersScreen(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(parent.getScene());
    }

    @FXML
    void processOrder(ActionEvent event) {
        String selectionTransportService = transportServicesBox.getSelectionModel().getSelectedItem();
        if (selectionTransportService == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You need to select a transport service in order to be able to process order");
            alert.showAndWait();
            return;
        }
        domainController.processOrder(id, selectionTransportService);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(parent.getScene());
    }

}
