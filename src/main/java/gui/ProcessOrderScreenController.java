import domain.DomainController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProcessOrderScreenController extends GridPane{

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
    private ObservableList<String> transportServicesObservableList;
    
    public ProcessOrderScreenController(DomainController dc,ProcessOrdersScreenController parent,int id) {
    	this.dc = dc;
    	this.parent=parent;
    	this.transportServicesObservableList = new 
    	
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ProcessOrderScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
        	loader.load();
        } catch (Exception e) {
			System.out.println(e);
		}
        
        overviewOrderLabel.setText("Overview order:"+Integer.toString(id));
        orderTextArea.setText(dc.giveOverviewOrder(id));
        choiceBoxTransportServices.setv
    }

    @FXML
    void backToProcessOrdersScreen(ActionEvent event) {
    	Stage stage = (Stage) this.getScene().getWindow();
    	stage.setScene(parent.getScene());
    }

    @FXML
    void processOrder(ActionEvent event) {
    	
    }

}
