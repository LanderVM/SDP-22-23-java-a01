package gui;

import domain.DomainController;
import domain.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProcessOrdersScreenController extends GridPane{


    @FXML
    private TableColumn<Order, String> companyCol;

    @FXML
    private TableColumn<Order, String> dateCol;

    @FXML
    private TableColumn<Order, Number> idCol;

    @FXML
    private TableView<Order> processableOrdersTable;
    
    @FXML
    private Button backToOverviewButton;

    private DomainController dc;
    private WarehousemanOverviewScreenController parent;
    
    public ProcessOrdersScreenController(DomainController dc,WarehousemanOverviewScreenController parent) {
    	this.dc=dc;
    	this.parent=parent;
    	
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ProcessOrdersScreen.fxml"));
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
        
        processableOrdersTable.setItems(dc.getObservableOrdersList());
        
        processableOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder)->{
        	int id = newOrder.getId();
        	ProcessOrderScreenController processOrderScreenController = new ProcessOrderScreenController(this.dc,this,id);
        	Scene scene = new Scene(processOrderScreenController);
        	Stage stage = (Stage)this.getScene().getWindow();
        	stage.setScene(scene);
        	stage.show();
        });
    }
    
    @FXML
    void backToOverview(ActionEvent event) {
    	Stage stage = (Stage)this.getScene().getWindow();
    	stage.setScene(parent.getScene());
    }
}
