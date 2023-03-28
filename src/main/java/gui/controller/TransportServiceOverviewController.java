package gui.controller;

import domain.TransportServiceController;
import domain.UserController;
import exceptions.EntityDoesntExistException;
import gui.view.ContactPersonView;
import gui.view.TransportServiceView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
	private Label lblCurrentAction;
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
	private Button btnCreateService;
	@FXML
	private Button btnAddContactPerson;
	@FXML
	private Button btnRemoveContactPerson;
	@FXML
	private Button btnCurrentActionCreate;
	@FXML
	private Button btnCurrentActionSave;

	private final UserController userController;
	private final TransportServiceController transportServiceController;
	private int transportServiceId = -1; // TODO
	private String transportServiceName;
	private boolean currentActionCreate;
	private ObservableList<ContactPersonView> listForAddedContactPersons;
	private ObservableList<ContactPersonView> listForAllContactPersons;
	private String selectedContactPersonEmail;

	public TransportServiceOverviewController(UserController userController,
			TransportServiceController transportServiceController) {
		this.userController = userController;
		this.transportServiceController = transportServiceController;
	}

	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
		lblCurrentAction.setText("Current action: update a service");
		currentActionCreate = false;
		btnCurrentActionSave.setDisable(true);
		btnCreateService.setVisible(false);
		btnSave.setDisable(true);
		btnAddContactPerson.setDisable(true);
		btnRemoveContactPerson.setDisable(true);

		listForAllContactPersons = FXCollections.observableArrayList();
		listForAddedContactPersons = FXCollections.observableArrayList();

		ChoiceBoxExtraVerificationCode.setItems(TransportServiceView.getVerficationTypesObservableList());

		// Table TransportService
		tblTransportservicesClmName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblTransportservicesClmStatus.setCellValueFactory(
				cellData -> cellData.getValue().activeProperty().get() ? new SimpleStringProperty("Active")
						: new SimpleStringProperty("Inactive"));

		tblTransportServices.setItems(transportServiceController.getTransportServices(userController.supplierIdFromUser()));

		tblTransportServices.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldService, newService) -> {
					if (newService != null) {

						listForAddedContactPersons.clear();
						listForAllContactPersons.clear();

						btnSave.setDisable(false);
						btnAddContactPerson.setDisable(false);
						btnRemoveContactPerson.setDisable(true);
						transportServiceName = newService.getName();
						transportServiceId = newService.getTransportServiceId();

						// Table info TransportService
						txtName.setText(newService.getName());
						txtCharacterAmount.setText(newService.characterCountProperty().getValue().toString());
						txtPrefix.setText(newService.getPrefix());
						chkboxIsActive.setSelected(newService.isActive());
						chkboxOnlyNumbers.setSelected(newService.isIntegersOnly());
						ChoiceBoxExtraVerificationCode.setValue(newService.getVerificationType().toString());

						// Table ContactPerson TransportService
						tblContactPersonClmPhone
								.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
						tblContactPersonClmEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
						listForAllContactPersons.addAll(newService.getContactPeople());
						tblContactPerson.setItems(listForAllContactPersons);

						tblContactPerson.getSelectionModel().selectedItemProperty()
								.addListener((observableValue2, oldContactPerson, newContactPerson) -> {
									btnRemoveContactPerson.setDisable(false);
									selectedContactPersonEmail = newContactPerson.getEmail();
								});
					}
				});
	}

	@FXML
	private void saveTransportServices() {

		try {
			transportServiceController.updateTransportService(transportServiceId, txtName.getText(),
					listForAllContactPersons, Integer.parseInt(txtCharacterAmount.getText()),
					chkboxOnlyNumbers.isSelected(), txtPrefix.getText(),
					ChoiceBoxExtraVerificationCode.getSelectionModel().getSelectedItem(), chkboxIsActive.isSelected());
		} catch (NumberFormatException e) {
			e.printStackTrace(); // TODO
		} catch (EntityDoesntExistException e) {
			e.printStackTrace();
		}
		reselectTransportService(transportServiceId);
	}

	@FXML
	private void addToContactPersonsList() {
		if (!currentActionCreate) {
			if (txtAddEmail.getText().isBlank() || txtAddPhoneNumber.getText().isBlank()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Incorrect data");
				alert.setHeaderText(null);
				alert.setContentText("Contact person needs an email address and phonenumber");
				alert.showAndWait();
			}

			// checkIfContactPersonIsAlreadyAdded(txtAddEmail.getText(),
			// txtAddPhoneNumber.getText());

			ContactPersonView contactPersonView = new ContactPersonView(txtAddEmail.getText(),
					txtAddPhoneNumber.getText());
			listForAllContactPersons.add(contactPersonView);
			listForAddedContactPersons.add(contactPersonView);
		} else {
			if (txtAddEmail.getText().isBlank() || txtAddPhoneNumber.getText().isBlank()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Incorrect data");
				alert.setHeaderText(null);
				alert.setContentText("Contact person needs an email address and phonenumber");
				alert.showAndWait();
			}

			ContactPersonView contactPersonView = new ContactPersonView(txtAddEmail.getText(),
					txtAddPhoneNumber.getText());
			listForAllContactPersons.add(contactPersonView);
		}
	}

	@FXML
	void removeContactPerson() {
		if (!currentActionCreate) {
			boolean isAddedContactPerson = false;
			for (ContactPersonView cpv : listForAddedContactPersons) {
				if (cpv.getEmail().equals(selectedContactPersonEmail))
					isAddedContactPerson = true;
			}
			if (isAddedContactPerson) {
				int indexCvp = 0;
				for (int i = 0; i < listForAddedContactPersons.size(); i++) {
					if (listForAddedContactPersons.get(i).getEmail().equals(selectedContactPersonEmail))
						indexCvp = i;
				}
				listForAddedContactPersons.remove(indexCvp);
				for (int i = 0; i < listForAllContactPersons.size(); i++) {
					if (listForAllContactPersons.get(i).getEmail().equals(selectedContactPersonEmail))
						indexCvp = i;
				}
				listForAllContactPersons.remove(indexCvp);
			} else {
				int indexCvp = 0;
				for (int i = 0; i < listForAllContactPersons.size(); i++) {
					if (listForAllContactPersons.get(i).getEmail().equals(selectedContactPersonEmail))
						indexCvp = i;
				}
				listForAllContactPersons.remove(indexCvp);
			}
		} else {
			int indexCvp = 0;
			for (int i = 0; i < listForAllContactPersons.size(); i++) {
				if (listForAllContactPersons.get(i).getEmail().equals(selectedContactPersonEmail))
					indexCvp = i;
			}
			listForAllContactPersons.remove(indexCvp);
		}
	}

	@FXML
	private void createService() {
		try {
			transportServiceController.addTransportService(txtName.getText(), listForAllContactPersons,
					Integer.parseInt(txtCharacterAmount.getText()), chkboxOnlyNumbers.isSelected(), txtPrefix.getText(),
					ChoiceBoxExtraVerificationCode.getSelectionModel().getSelectedItem(), chkboxIsActive.isSelected(),
					userController.supplierIdFromUser());
			initializeSaveTransportService();
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Incorrect data");
			alert.setHeaderText(null);
			alert.setContentText("Please fill in all the fields");
			alert.showAndWait();
		} catch (IllegalArgumentException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Incorrect data");
			alert.setHeaderText(null);
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	void switchActionToCreate() {
		initializeCreateTransportService();
	}

	@FXML
	void switchActionToSave() {
		initializeSaveTransportService();
	}

	private void reselectTransportService(int tsId) {
		int index = 0;
		for (int i = 0; i < tblTransportServices.getItems().size(); i++) {
			if (tblTransportServices.getItems().get(i).getTransportServiceId() == tsId) {
				index = i;
				break;
			}
		}
		tblTransportServices.getSelectionModel().select(index);
	}

	@FXML
	private void showEmployees() {
		FXStageUtil.setScene(TransportServiceController.class.getResource("/gui/EmployeesOverview.fxml"), "Employees");
	}

	@FXML
	private void showBoxes() {
		FXStageUtil.setScene(TransportServiceController.class.getResource("/gui/PackagingOverview.fxml"), "Packaging");
	}

	@FXML
	private void logOut() {
		FXStageUtil.setScene(TransportServiceController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
	}

	private void initializeCreateTransportService() {
		lblCurrentAction.setText("Current action: creating a service");
		tblContactPerson.getSelectionModel().clearSelection();
		tblTransportServices.getSelectionModel().clearSelection();
		listForAddedContactPersons.clear();
		listForAllContactPersons.clear();
		btnAddContactPerson.setDisable(false);
		tblTransportServices.setVisible(false);
		currentActionCreate = true;
		btnCurrentActionCreate.setDisable(true);
		btnCurrentActionSave.setDisable(false);
		btnCreateService.setVisible(true);
		btnSave.setVisible(false);
		tblContactPersonClmPhone.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
		tblContactPersonClmEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		tblContactPerson.setItems(listForAllContactPersons);
		tblContactPerson.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldContactPerson, newContactPerson) -> {
					btnRemoveContactPerson.setDisable(false);
					selectedContactPersonEmail = newContactPerson.getEmail();
				});

	}

	private void initializeSaveTransportService() {
		lblCurrentAction.setText("Current action: updating a service");
		tblContactPerson.getSelectionModel().clearSelection();
		tblTransportServices.getSelectionModel().clearSelection();
		listForAddedContactPersons.clear();
		listForAllContactPersons.clear();
		tblTransportServices.setVisible(true);
		btnAddContactPerson.setDisable(true);
		currentActionCreate = false;
		btnCurrentActionCreate.setDisable(false);
		btnCurrentActionSave.setDisable(true);
		btnCreateService.setVisible(false);
		btnSave.setVisible(true);
		tblTransportServices.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldService, newService) -> {
					if (newService != null) {

						listForAddedContactPersons.clear();
						listForAllContactPersons.clear();

						btnSave.setDisable(false);
						btnAddContactPerson.setDisable(false);
						btnRemoveContactPerson.setDisable(true);
						transportServiceName = newService.getName();
						transportServiceId = newService.getTransportServiceId();

						// Table info TransportService
						txtName.setText(newService.getName());
						txtCharacterAmount.setText(newService.characterCountProperty().getValue().toString());
						txtPrefix.setText(newService.getPrefix());
						chkboxIsActive.setSelected(newService.isActive());
						chkboxOnlyNumbers.setSelected(newService.isIntegersOnly());
						ChoiceBoxExtraVerificationCode.setValue(newService.getVerificationType().toString());

						// Table ContactPerson TransportService
						tblContactPersonClmPhone
								.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
						tblContactPersonClmEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
						listForAllContactPersons.addAll(newService.getContactPeople());
						tblContactPerson.setItems(listForAllContactPersons);

						tblContactPerson.getSelectionModel().selectedItemProperty()
								.addListener((observableValue2, oldContactPerson, newContactPerson) -> {
									btnRemoveContactPerson.setDisable(false);
									selectedContactPersonEmail = newContactPerson.getEmail();
								});
					}
				});
	}

}
