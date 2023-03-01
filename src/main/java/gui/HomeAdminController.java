package gui;

import domain.OrderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class HomeAdminController extends AnchorPane{

    @FXML
    private Button btnManageEmployees;
    @FXML
    private Button btnManageBoxes;
    @FXML
    private Button btnManageTransportServices;

	private final OrderController orderController;

	public HomeAdminController(OrderController orderController) {
		this.orderController = orderController;
	}
	
	@FXML
	void manageEmployees() {
		//C
	} 

	@FXML
	void manageBoxes() {
		//C
	} 

	@FXML
	void manageTransportServices() {
		//TO DO
	} 
	
}
