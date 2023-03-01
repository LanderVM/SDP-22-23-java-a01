package gui.controller;

import domain.DomainController;
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
    
	private DomainController dc;
    
	public HomeAdminController(DomainController dc) {
		this.dc = dc;
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
