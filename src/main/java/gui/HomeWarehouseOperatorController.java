package gui;

import java.io.IOException;

import domain.OrderController;
import domain.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeWarehouseOperatorController extends AnchorPane{

	    @FXML
	    private Button btnViewCustomers;
	    @FXML
	    private Button btnViewOrders;
	    @FXML
	    private Button btnChangeTrackAndTrace;
	    
		private OrderController oc;
		private UserController uc;
	    
		public HomeWarehouseOperatorController(OrderController oc, UserController uc) {
			this.oc = oc;
			this.uc = uc;
		}
		
		@FXML
		void viewCustomers() {
			//C
		} 

		@FXML
		void viewOrders() {
			OrdersOverviewController ordersOverviewController = new OrdersOverviewController(oc, uc);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrdersOverview.fxml"));

			loader.setRoot(ordersOverviewController);
			loader.setController(ordersOverviewController);
			try {
				loader.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			Scene scene = new Scene(ordersOverviewController);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Orders");
			stage.show();
		} 

		@FXML
		void changeTrackAndTrace() {
			//C
		} 
}
