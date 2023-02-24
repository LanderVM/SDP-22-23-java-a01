package gui;

import domain.DomainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class WarehousemanOverviewScreenController extends GridPane{

    @FXML
    private Button exitApplicationButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button processOrderButton;

    @FXML
    private Button viewCustomersButton;

    @FXML
    private Button viewOrdersButton;
    
    private DomainController dc;
    private LoginScreenController parent;
    
    public WarehousemanOverviewScreenController(DomainController dc, LoginScreenController parent) {
    	
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("warehousemanOverviewScreen.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	this.dc = dc;
    	this.parent = parent;
    }

    @FXML
    void exitApplication(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void processOrder(ActionEvent event) {

    }

    @FXML
    void viewCustomers(ActionEvent event) {

    }

    @FXML
    void viewOrders(ActionEvent event) {

    }

}
