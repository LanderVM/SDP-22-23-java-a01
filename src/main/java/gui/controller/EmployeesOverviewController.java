package gui.controller;

import domain.UserController;
import exceptions.InvalidNameException;
import exceptions.InvalidUserEmailException;
import exceptions.UserAlreadyExistsExeption;
import gui.view.UserDTO;
import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import util.FXStageUtil;

public class EmployeesOverviewController extends GridPane{

	@FXML
	public Label lblUser;
	@FXML
	public GridPane GridPanePersonalInfo;
	@FXML
	public Label lblName;
	@FXML
	private ImageView imgDelaware;
	@FXML
	private Label LblUser;
	@FXML
	private Hyperlink btnTransportServices;
	@FXML
	private Hyperlink btnBoxes;
	@FXML
	private TableView<UserDTO> tblEmployees;
	@FXML
	private TableColumn<UserDTO, String> tblEmployeesColumnName;
	@FXML
	private TableColumn<UserDTO, String> tblEmployeesColumnFunction;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtTelephone;
	@FXML
	private TextField txtMobilePhone;
	@FXML
	private TextField txtEmail;
	@FXML
	private ChoiceBox<String> choiceBoxFunction;
	@FXML
	private TextField txtStreet;
	@FXML
	private TextField txtNumber;
	@FXML
	private TextField txtBox;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtPostalCode;
	@FXML
	private TextField txtCountry;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnNew;
	@FXML
	private Button btnClear;
	
    private final UserController userController;
    private int id = -1; // TODO check out
    private String email;

	public EmployeesOverviewController(UserController userController) {
		this.userController = userController;
	}

	@FXML
	private void initialize() {

		lblUser.setText(userController.toString());
		btnSave.setDisable(true);

		tblEmployeesColumnName.setCellValueFactory(cellData -> cellData.getValue().getAccountNameProperty());
		tblEmployeesColumnFunction.setCellValueFactory(cellData -> cellData.getValue().getFunctionProperty());

		choiceBoxFunction.setItems(UserDTO.getRollesObservableList());

		refreshEmployeesList();

		tblEmployees.getSelectionModel().selectedItemProperty()
		.addListener((observableValue, oldEmployee, newEmployee) -> {

			if (newEmployee != null) {

				btnSave.setDisable(false);
				btnNew.setDisable(false);
				email = newEmployee.getAccountName();
				id = newEmployee.getEmployeeId();

				// Table info TransportService
				txtName.setText(newEmployee.getName());
				txtFirstName.setText(newEmployee.getSurname());
				txtTelephone.setText(newEmployee.getTelephone());
				txtMobilePhone.setText(newEmployee.getMobilePhone());
				txtEmail.setText(newEmployee.getAccountName());
				choiceBoxFunction.setValue(newEmployee.getFunction());

				txtStreet.setText(newEmployee.getStreet());
				txtNumber.setText(newEmployee.getHouseNumber());
				txtBox.setText(newEmployee.getBox());
				txtCity.setText(newEmployee.getCity());
				txtPostalCode.setText(newEmployee.getPostalCode());
				txtCountry.setText(newEmployee.getCountry());
			}

		});
	}



	private void refreshEmployeesList() {
		tblEmployees.setItems(userController.getEmployees());		
	}
	
	@FXML 
	private void showCarrier() {		
		FXStageUtil.setScene(EmployeesOverviewController.class.getResource("/gui/TransportServiceOverview.fxml"), "Transport Service");
	}
	
	@FXML 
	private void logOut() {
		FXStageUtil.setScene(EmployeesOverviewController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
	}
	
	@FXML 
	private void showBoxes() {	
		FXStageUtil.setScene(EmployeesOverviewController.class.getResource("/gui/PackagingOverview.fxml"), "Packaging");
	}
	
	@FXML 
	private void saveEmployees() {
		try {
			if(!txtEmail.getText().equals(email)) {				
				throw new InvalidUserEmailException();
			}
			userController.updateUser(txtEmail.getText(), txtFirstName.getText(), txtName.getText(), txtTelephone.getText(), txtMobilePhone.getText(),
					choiceBoxFunction.getValue(), txtStreet.getText(), Integer.parseInt(txtNumber.getText()), txtBox.getText(), txtCity.getText(), txtPostalCode.getText(),
					txtCountry.getText());
			showInfo("Employee updated succesfully", String.format("Updated user %s", email));
		} catch (InvalidUserEmailException e) {
			showError("Email changed", "The email cannot be changed");
		} catch (NumberFormatException e) {
			showError("Invalid house number", "Please insert a valid number");
		} catch (IllegalArgumentException e) {
			showError("Input error", e.getMessage());
		} catch (NoResultException ex) {
			showError("Error with update", String.format("Cannot update user with email: %s, because it does not exists.", email));
		} catch (Exception e) {
			showError("Error", "Something went wrong while trying to update a employee");
			System.out.println(e);
		}
		refreshEmployeesList();	
	}
	
	@FXML 
	private void addEmployees() {
		try {		
			userController.addUser(txtEmail.getText(), txtFirstName.getText(), txtName.getText(), txtTelephone.getText(), txtMobilePhone.getText(),
					choiceBoxFunction.getValue(), txtStreet.getText(), Integer.parseInt(txtNumber.getText()), txtBox.getText(), txtCity.getText(), txtPostalCode.getText(),
					txtCountry.getText(), userController.getSupplier());
			showInfo("Employee created succesfully", String.format("created user %s", email));
		} catch (UserAlreadyExistsExeption e) {
			showError("Email already exists", String.format("There already exists a user with the email address of: %s", txtEmail.getText()));
		} catch (NumberFormatException e) {
			showError("Invalid house number", "Please insert a valid house number");
		} catch (InvalidNameException e) {
			showError("Empty field error", e.getMessage());
		} catch (Exception e) {
			showError("Error", "Something went wrong while trying to create a new employee");
			System.out.println(e);
		}
		refreshEmployeesList();
	}
	
	@FXML
	private void clearFields() {
		txtName.clear();
		txtFirstName.clear();			
		txtTelephone.clear();
		txtMobilePhone.clear();
		txtEmail.clear();
		choiceBoxFunction.setValue(null);
		
		txtStreet.clear();
		txtNumber.clear();
		txtBox.clear();
		txtCity.clear();
		txtPostalCode.clear();
		txtCountry.clear();
	}
	
	private void showInfo(String title, String details) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(details);
		alert.showAndWait();
	}
	
	private void showError(String title, String details) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(details);
		alert.showAndWait();
	}

}