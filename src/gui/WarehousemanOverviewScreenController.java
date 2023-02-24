package gui;

import domain.DomainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
    
    
    public WarehousemanOverviewScreenController(DomainController dc) {
    	
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("WarehousemanOverviewScreen.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	this.dc = dc;
    	
    }

    @FXML
    void exitApplication(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void logout(ActionEvent event) {
    	LoginScreenController loginScreen = new LoginScreenController(new DomainController());
    	Stage stage = (Stage)this.getScene().getWindow();
    	Scene scene = new Scene(loginScreen);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void processOrder(ActionEvent event) {
    	//TODO
    }

    @FXML
    void viewCustomers(ActionEvent event) {
    	//TODO
    }

    @FXML
    void viewOrders(ActionEvent event) {
    	//TODO
    }

}
