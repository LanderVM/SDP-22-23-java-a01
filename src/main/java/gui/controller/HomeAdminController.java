package gui.controller;

import domain.OrderController;
import domain.UserController;
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

	private final OrderController oc;
	private UserController uc;

	public HomeAdminController(OrderController oc, UserController uc) {
		this.oc = oc;
		this.uc = uc;
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
