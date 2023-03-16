package gui.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import domain.ContactPerson;
import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import domain.VerificationType;
import gui.view.ContactPersonView;
import gui.view.TransportServiceView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddTransportService extends GridPane {

	@FXML
	private Label lblUser;
	@FXML
	private TableView<TransportServiceView> tblTransportServices;
	@FXML
	private TableColumn<TransportServiceView, String> tblTransportservicesClmName;
	@FXML
	private TableColumn<TransportServiceView, Boolean> tblTransportservicesClmStatus;
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

	private OrderController orderController;
	private UserController userController;
	private SupplierController supplierController;
	private TransportServiceController transportServiceController;
	private int id = -1;
	private String name;

	public AddTransportService(OrderController orderController, UserController userController,
			SupplierController supplierController, TransportServiceController transportServiceController) {
		this.orderController = orderController;
		this.userController = userController;
		this.supplierController = supplierController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
		
		List<String> typeList = Arrays.stream(VerificationType.values()).map(VerificationType::name)
				.collect(Collectors.toList());
		ObservableList<String> verificationTypes = FXCollections.observableList(typeList);
		ChoiceBoxExtraVerificationCode.setItems(verificationTypes);

		// Table TransportService
		tblTransportservicesClmName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblTransportservicesClmStatus.setCellValueFactory(cellData -> cellData.getValue().activeProperty());

		refreshCustomersList();

		tblTransportServices.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldCostumer, newCustomer) -> {

					name = newCustomer.getName();
					id = newCustomer.getTransportServiceId();

					// Table info TransportService
					txtName.setText(newCustomer.getName());
					txtCharacterAmount.setText(newCustomer.characterCountProperty().getValue().toString());
					txtPrefix.setText(newCustomer.getPrefix());
					chkboxIsActive.setSelected(newCustomer.isActive());
					chkboxOnlyNumbers.setSelected(newCustomer.isIntegersOnly());
					ChoiceBoxExtraVerificationCode.setValue(newCustomer.getVerificationType().toString());

					// Table ContactPersonSupplier
					tblContactPersonClmPhone.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
					tblContactPersonClmEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
					tblContactPerson.setItems(FXCollections.observableArrayList(newCustomer.getContactPeople()));
				});
	}

	public void refreshCustomersList() {
		tblTransportServices
				.setItems(FXCollections.observableArrayList(transportServiceController.getTransportServices()));
	}

	@FXML
	private void saveTransportServices() {
		List<ContactPerson> contactPersonList = transportServiceController.getTransportServiceByName(name)
				.getContactPersonList();
		transportServiceController.updateTransportService(id, txtName.getText(), contactPersonList,
				Integer.parseInt(txtCharacterAmount.getText()), chkboxOnlyNumbers.isSelected(), txtPrefix.getText(),
				ChoiceBoxExtraVerificationCode.getSelectionModel().getSelectedItem(), chkboxIsActive.isSelected());
		refreshCustomersList();
	}

	@FXML
	private void showHome() {

	}

	@FXML
	private void showNotifications() {

	}

	@FXML
	private void showOrders() {

	}

	@FXML
	private void addToContactPersonsList() {
		transportServiceController.addTransportService(getAccessibleText(), null, id, isDisable(),
				getAccessibleRoleDescription(), getAccessibleHelp(), isCache());
	}

}
