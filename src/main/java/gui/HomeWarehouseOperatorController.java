package gui;

import domain.OrderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class HomeWarehouseOperatorController extends AnchorPane{

	    @FXML
	    private Button btnViewCustomers;
	    @FXML
	    private Button btnViewOrders;
	    @FXML
	    private Button btnChangeTrackAndTrace;
	    
		private OrderController oc;
	    
		public HomeWarehouseOperatorController(OrderController oc) {
			this.oc = oc;
		}
		
		@FXML
		void viewCustomers() {
			//C
		} 

		@FXML
		void viewOrders() {
			//C
		} 

		@FXML
		void changeTrackAndTrace() {
			//TO DO
		} 
}
