package gui;

import domain.DomainController;
import domain.TransportService;
import exceptions.OrderStatusException;
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
    private Button backToProcessOrdersScreenButton;

    @FXML
    private GridPane baseGrid;

    @FXML
    private ChoiceBox<String> choiceBoxTransportServices;

    @FXML
    private TextArea orderTextArea;

    @FXML
    private Label overviewOrderLabel;

    @FXML
    private Button processOrderButton;

    private DomainController dc;
    private ProcessOrdersScreenController parent;
    private int id;


    public ProcessOrderScreenController(DomainController dc, ProcessOrdersScreenController parent, int id) {
        this.dc = dc;
        this.parent = parent;
        this.id = id;

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ProcessOrderScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }

        overviewOrderLabel.setText("Overview order: " + Integer.toString(id));
        orderTextArea.setText(dc.getOrderOverview(id));
        choiceBoxTransportServices.setItems(dc.getTransportServicesObservableList());
    }

    @FXML
    void backToProcessOrdersScreen(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(parent.getScene());
    }

    @FXML
    void processOrder(ActionEvent event) {
        String selectionTransportService = choiceBoxTransportServices.getSelectionModel().getSelectedItem();
        if (selectionTransportService == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You need to select a transport service in order to be able to process order");
            alert.showAndWait();
            return;
        }
        try {
            // TODO proper exception handling
            dc.processOrder(id, TransportService.valueOf(selectionTransportService));
        } catch (OrderStatusException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(parent.getScene());
    }

}
