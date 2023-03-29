package gui.controller;

import domain.CarrierController;
import domain.UserController;
import exceptions.EntityDoesntExistException;
import gui.view.ContactPersonView;
import gui.view.CarrierDTO;
import jakarta.persistence.NoResultException;
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

import java.util.Objects;

public class CarrierViewController extends GridPane {

	@FXML
	private Label lblUser;
	@FXML
	private Label lblCurrentAction;
	@FXML
	private TableView<CarrierDTO> tblCarriers;
	@FXML
	private TableColumn<CarrierDTO, String> tblCarrierClmName;
	@FXML
	private TableColumn<CarrierDTO, String> tblCarriersClmStatus;
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
	private final CarrierController carrierController;
	private int carrierId = -1;
	private boolean currentActionCreate;
	private ObservableList<ContactPersonView> listForAddedContactPersons;
	private ObservableList<ContactPersonView> listForAllContactPersons;
	private String selectedContactPersonEmail;

	public CarrierViewController(UserController userController,
								 CarrierController carrierController) {
		this.userController = userController;
		this.carrierController = carrierController;
	}

    private void showAlert(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@FXML
	private void initialize() {
		lblUser.setText(userController.toString());
		lblCurrentAction.setText("Current Action: Updating a Carrier");
		currentActionCreate = false;
		btnCurrentActionSave.setDisable(true);
		btnCreateService.setVisible(false);
		btnSave.setDisable(true);
		btnAddContactPerson.setDisable(true);
		btnRemoveContactPerson.setDisable(true);

		listForAllContactPersons = FXCollections.observableArrayList();
		listForAddedContactPersons = FXCollections.observableArrayList();

		ChoiceBoxExtraVerificationCode.setItems(CarrierDTO.getVerficationTypesObservableList());

		// Table Carrier
		tblCarrierClmName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblCarriersClmStatus.setCellValueFactory(cellData -> cellData.getValue().activeProperty());

		tblCarriers.setItems(carrierController.getCarriers(userController.supplierIdFromUser()));

		tblCarriers.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldService, newService) -> {
					if (newService != null) {

						listForAddedContactPersons.clear();
						listForAllContactPersons.clear();

						btnSave.setDisable(false);
						btnAddContactPerson.setDisable(false);
						btnRemoveContactPerson.setDisable(true);
						carrierId = newService.getCarrierId();

						// Table info Carrier
						txtName.setText(newService.getName());
						txtCharacterAmount.setText(newService.characterCountProperty().getValue().toString());
						txtPrefix.setText(newService.getPrefix());
						chkboxIsActive.setSelected(newService.isActive());
						chkboxOnlyNumbers.setSelected(newService.isIntegersOnly());
						ChoiceBoxExtraVerificationCode.setValue(newService.getVerificationType().toString());

						// Table ContactPerson Carrier
						tblContactPersonClmPhone
								.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
						tblContactPersonClmEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
						listForAllContactPersons.addAll(newService.getContactPeople());
						tblContactPerson.setItems(listForAllContactPersons);

						tblContactPerson.getSelectionModel().selectedItemProperty()
								.addListener((observableValue2, oldContactPerson, newContactPerson) -> {
                                    if (newContactPerson == null)
                                        return;
									btnRemoveContactPerson.setDisable(false);
									selectedContactPersonEmail = newContactPerson.getEmail();
								});
					}
				});
	}

	@FXML
	private void saveCarrier() {
		try {
			carrierController.updateCarrier(carrierId, txtName.getText(),
					listForAllContactPersons, Integer.parseInt(txtCharacterAmount.getText()),
					chkboxOnlyNumbers.isSelected(), txtPrefix.getText(),
					ChoiceBoxExtraVerificationCode.getSelectionModel().getSelectedItem(), chkboxIsActive.isSelected());
			showAlert("Successful", "Changes to Carrier have been saved.", AlertType.INFORMATION);
		} catch (NumberFormatException numberFormatException) {
			showAlert("Invalid Input", "Character amount input must be a valid number!", AlertType.ERROR);
		} catch (NoResultException | EntityDoesntExistException e) {
			showAlert("Internal Error", "Database did not find any Carrier with this id!", AlertType.ERROR);
		} catch (IllegalArgumentException illegalArgumentException) {
			showAlert("Invalid Action", illegalArgumentException.getMessage(), AlertType.ERROR);
		}
		reselectCarrier(carrierId);
	}

	@FXML
	private void addToContactPersonsList() {
			if (txtAddEmail.getText().isBlank() || txtAddPhoneNumber.getText().isBlank()) {
                showAlert("Invalid Input", "Phone number and email address must be filled in!", AlertType.ERROR);
				return;
			}
			ContactPersonView contactPerson = new ContactPersonView(txtAddEmail.getText(), txtAddPhoneNumber.getText());
			listForAllContactPersons.add(contactPerson);
			if (!currentActionCreate)
				listForAddedContactPersons.add(contactPerson);
	}

	@FXML
	void removeContactPerson() {
        if (!currentActionCreate)
            listForAddedContactPersons.removeIf(contactPerson -> Objects.equals(contactPerson.getEmail(), selectedContactPersonEmail));
        listForAllContactPersons.removeIf(contactPerson -> Objects.equals(contactPerson.getEmail(), selectedContactPersonEmail));
	}

	@FXML
	private void createService() {
		try {
			carrierController.addCarrier(txtName.getText(), listForAllContactPersons,
					Integer.parseInt(txtCharacterAmount.getText()), chkboxOnlyNumbers.isSelected(), txtPrefix.getText(),
					ChoiceBoxExtraVerificationCode.getSelectionModel().getSelectedItem(), chkboxIsActive.isSelected(),
					userController.supplierIdFromUser());
			showAlert("Creation Successful", "Carrier has been added.", AlertType.INFORMATION);
			initializeSaveCarrier();
		} catch (NumberFormatException e) {
			showAlert("Invalid Input", "Character amount input must be a valid number!", AlertType.ERROR);
		} catch (IllegalArgumentException illegalArgumentException) {
			showAlert("Invalid Input", illegalArgumentException.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	void switchActionToCreate() {
		initializeCreateCarrier();
	}

	@FXML
	void switchActionToSave() {
		initializeSaveCarrier();
	}

	private void reselectCarrier(int carrierId) {
		for (CarrierDTO carrierView : tblCarriers.getItems())
			if (carrierView.getCarrierId() == carrierId) {
                tblCarriers.getSelectionModel().select(carrierView);
				break;
			}
	}

	@FXML
	private void showEmployees() {
		FXStageUtil.setScene(CarrierController.class.getResource("/gui/EmployeesOverview.fxml"), "Employees");
	}

	@FXML
	private void showBoxes() {
		FXStageUtil.setScene(CarrierController.class.getResource("/gui/PackagingOverview.fxml"), "Packaging");
	}

	@FXML
	private void logOut() {
		FXStageUtil.setScene(CarrierController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
	}

	private void initializeCreateCarrier() {
		lblCurrentAction.setText("Current Action: Adding a Carrier");
		tblContactPerson.getSelectionModel().clearSelection();
		tblCarriers.getSelectionModel().clearSelection();
		listForAddedContactPersons.clear();
		listForAllContactPersons.clear();
		btnAddContactPerson.setDisable(false);
		tblCarriers.setVisible(false);
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

	private void initializeSaveCarrier() {
		lblCurrentAction.setText("Current Action: Updating a Carrier");
		tblContactPerson.getSelectionModel().clearSelection();
		tblCarriers.getSelectionModel().clearSelection();
		listForAddedContactPersons.clear();
		listForAllContactPersons.clear();
		tblCarriers.setVisible(true);
		btnAddContactPerson.setDisable(true);
		currentActionCreate = false;
		btnCurrentActionCreate.setDisable(false);
		btnCurrentActionSave.setDisable(true);
		btnCreateService.setVisible(false);
		btnSave.setVisible(true);
		tblCarriers.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldService, newService) -> {
					if (newService != null) {

						listForAddedContactPersons.clear();
						listForAllContactPersons.clear();

						btnSave.setDisable(false);
						btnAddContactPerson.setDisable(false);
						btnRemoveContactPerson.setDisable(true);
						carrierId = newService.getCarrierId();

						// Table info Carrier
						txtName.setText(newService.getName());
						txtCharacterAmount.setText(newService.characterCountProperty().getValue().toString());
						txtPrefix.setText(newService.getPrefix());
						chkboxIsActive.setSelected(newService.isActive());
						chkboxOnlyNumbers.setSelected(newService.isIntegersOnly());
						ChoiceBoxExtraVerificationCode.setValue(newService.getVerificationType().toString());

						// Table ContactPerson Carrier
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
