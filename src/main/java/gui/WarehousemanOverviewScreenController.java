package gui;

import java.util.Optional;

import domain.DomainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    	
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/WarehousemanOverviewScreen.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	
    	this.dc = dc;
    	
    	try {
        	loader.load();
        } catch (Exception e) {
			System.out.println(e);
		}
    }

    @FXML
    void exitApplication(ActionEvent event) {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation");
    	alert.setHeaderText("You sure you want to exit application?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get()==ButtonType.OK) {
    		Platform.exit();
    	}
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

    	ProcessOrdersScreenController processOrdersScreenController = new ProcessOrdersScreenController(this.dc,this);
    	Scene scene = new Scene(processOrdersScreenController);
    	Stage stage = (Stage)this.getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    	
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
