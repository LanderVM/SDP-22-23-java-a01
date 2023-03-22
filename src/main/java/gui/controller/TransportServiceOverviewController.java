package gui.controller;

import java.util.List;

import domain.ContactPerson;
import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import gui.view.ContactPersonView;
import gui.view.TransportServiceView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import util.FXStageUtil;

public class TransportServiceOverviewController extends GridPane {

	@FXML
	private Label lblUser;
	@FXML
	private TableView<TransportServiceView> tblTransportServices;
	@FXML
	private TableColumn<TransportServiceView, String> tblTransportservicesClmName;
	@FXML
	private TableColumn<TransportServiceView, String> tblTransportservicesClmStatus;
	@FXML
	private TableView<ContactPersonView> tblContactPerson;
	@FXML
	private TableColumn<ContactPersonView, String> tblContactPersonClmPhone;
	@FXML
	private TableColumn<ContactPersonView, String> tblContactPersonClmEmail;
	@FXML
	private CheckBox chkboxIsActive;
	@FXML
	private CheckBox chkboxOnlyNumbers;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtCharacterAmount;
	@FXML
	private TextField txtPrefix;
	@FXML
	private ChoiceBox<String> ChoiceBoxExtraVerificationCode;
	@FXML
	private TextField txtAddPhoneNumber;
	@FXML
	private TextField txtAddEmail;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnAddContactPerson;

	private OrderController orderController;
	private UserController userController;
	private SupplierController supplierController;
	private TransportServiceController transportServiceController;
	private int id = -1;
	private String name;

	public TransportServiceOverviewController(OrderController orderController, UserController userController,
			SupplierController supplierController, TransportServiceController transportServiceController) {
		this.orderController = orderController;
		this.userController = userController;
		this.supplierController = supplierController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
		btnSave.setDisable(true);
		btnAddContactPerson.setDisable(true);

		ChoiceBoxExtraVerificationCode.setItems(TransportServiceView.getVerficationTypesObservableList());

		// Table TransportService
		tblTransportservicesClmName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblTransportservicesClmStatus.setCellValueFactory(cellData -> cellData.getValue().activeProperty().get()==true?new SimpleStringProperty("active"):new SimpleStringProperty("not active"));

		refreshCustomersList();

		tblTransportServices.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldService, newService) -> {
					if (newService != null) {

					btnSave.setDisable(false);
					btnAddContactPerson.setDisable(false);
					name = newService.getName();
					id = newService.getTransportServiceId();

					// Table info TransportService
					txtName.setText(newService.getName());
					txtCharacterAmount.setText(newService.characterCountProperty().getValue().toString());
					txtPrefix.setText(newService.getPrefix());
					chkboxIsActive.setSelected(newService.isActive());
					chkboxOnlyNumbers.setSelected(newService.isIntegersOnly());
					ChoiceBoxExtraVerificationCode.setValue(newService.getVerificationType().toString());

					// Table ContactPerson TransportService
					tblContactPersonClmPhone.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
					tblContactPersonClmEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
					tblContactPerson.setItems(newService.getContactPeople());
					}
				});
	}

	public void refreshCustomersList() {
		tblTransportServices
				.setItems(transportServiceController.getTransportServices(userController.supplierIdFromUser()));
	}

	@FXML
	private void saveTransportServices() {
		List<ContactPerson> contactPersonList = transportServiceController.getTransportServiceByNameForSupplier(name, userController.supplierIdFromUser())
				.getContactPersonList();
		transportServiceController.updateTransportService(id, txtName.getText(), contactPersonList,
				Integer.parseInt(txtCharacterAmount.getText()), chkboxOnlyNumbers.isSelected(), txtPrefix.getText(),
				ChoiceBoxExtraVerificationCode.getSelectionModel().getSelectedItem(), chkboxIsActive.isSelected());
		int index = tblTransportServices.getSelectionModel().getFocusedIndex();
		refreshCustomersList();
		tblTransportServices.getSelectionModel().select(index);
	}

	@FXML
	private void showEmployees() {

	}

	@FXML
	private void showBoxes() {

	}

	@FXML
	private void addToContactPersonsList() {
		if (txtAddEmail.getText().isBlank() || txtAddPhoneNumber.getText().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Incorrect data");
			alert.setHeaderText(null);
			alert.setContentText("Contact person needs an email address and phonenumber");
			alert.showAndWait();
		}
		List<ContactPerson> contactPersonList = transportServiceController.getTransportServiceByNameForSupplier(name, userController.supplierIdFromUser()).getContactPersonList();
		contactPersonList.add(new ContactPerson(txtAddEmail.getText(), txtAddPhoneNumber.getText()));

		transportServiceController.updateTransportService(id, txtName.getText(), contactPersonList,
				Integer.parseInt(txtCharacterAmount.getText()), chkboxOnlyNumbers.isSelected(), txtPrefix.getText(),
				ChoiceBoxExtraVerificationCode.getSelectionModel().getSelectedItem(), chkboxIsActive.isSelected());
		int index = tblTransportServices.getSelectionModel().getFocusedIndex();
		refreshCustomersList();
		tblTransportServices.getSelectionModel().select(index);
	}
	
	@FXML
	private void logOut() {
		LoginScreenController loginScreenController = new LoginScreenController(orderController, userController, transportServiceController, supplierController);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginScreen.fxml"));
		FXStageUtil.change(loader, loginScreenController, "Log In");
	}

}
