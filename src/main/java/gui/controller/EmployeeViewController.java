package gui.controller;

import domain.UserController;
import exceptions.InvalidUserEmailException;
import exceptions.UserAlreadyExistsException;
import gui.view.UserDTO;
import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import util.FXStageUtil;

public class EmployeeViewController extends GridPane {

    @FXML
    public Label lblUser;
    @FXML
    public GridPane GridPanePersonalInfo;
    @FXML
    public Label lblName;
    @FXML
    public Label lblPostalCode;
    @FXML
    public Label lblCountry;
    @FXML
    public Label lblCity;
    @FXML
    public GridPane GridPaneAdressInfo;
    @FXML
    public Label lblBox;
    @FXML
    public Label lblStreet;
    @FXML
    public Label lblEmail;
    @FXML
    public Label lblNumber;
    @FXML
    public Label lblFunction;
    @FXML
    public Label lblPassword;
    @FXML
    public Label lblTelephone;
    @FXML
    public Label lblFirstName;
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
    private PasswordField txtPassword;
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
    private String email;
    private String password;

    public EmployeeViewController(UserController userController) {
        this.userController = userController;
    }

    @FXML
    private void initialize() {

        lblUser.setText(userController.toString());
        btnSave.setDisable(true);

        tblEmployeesColumnName.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
        tblEmployeesColumnFunction.setCellValueFactory(cellData -> cellData.getValue().functionProperty());

        choiceBoxFunction.setItems(UserDTO.getRolesObservableList());

        tblEmployees.setItems(userController.getEmployees());

        tblEmployees.getSelectionModel().selectedItemProperty().addListener((observableValue, oldEmployee, newEmployee) -> {

            if (newEmployee != null) {

                btnSave.setDisable(false);
                btnNew.setDisable(false);
                email = newEmployee.getAccountName();
                password = newEmployee.getPassword();

                // Table info Carriers
                txtName.setText(newEmployee.getName());
                txtFirstName.setText(newEmployee.getSurname());
                txtTelephone.setText(newEmployee.getTelephone());
                txtPassword.setText(newEmployee.getPassword());
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

    @FXML
    private void showCarrier() {
        FXStageUtil.setScene(EmployeeViewController.class.getResource("/gui/CarrierView.fxml"), "Carriers");
    }

    @FXML
    private void logOut() {
        FXStageUtil.setScene(EmployeeViewController.class.getResource("/gui/LoginView.fxml"), "Log In");
    }

    @FXML
    private void showBoxes() {
        FXStageUtil.setScene(EmployeeViewController.class.getResource("/gui/PackagingView.fxml"), "Packaging");
    }

    @FXML
    private void saveEmployees() {
    	
        try {
            if (!txtEmail.getText().equals(email) || !txtPassword.getText().equals(password)) {
                throw new InvalidUserEmailException();
            }
            
            
            userController.updateUser(txtEmail.getText(),txtPassword.getText(), txtFirstName.getText(), txtName.getText(), txtTelephone.getText(), choiceBoxFunction.getValue(), txtStreet.getText(), Integer.parseInt(txtNumber.getText()), txtBox.getText(), txtCity.getText(), txtPostalCode.getText(), txtCountry.getText());
            showInfo("Employee updated successfully", String.format("Updated user %s", email));
            
        } catch (InvalidUserEmailException e) {
            showError("Email/Password changed", "The email and/or password cannot be changed");
        } catch (NumberFormatException e) {
            showError("Invalid house number", "Please insert a valid number");
        } catch (IllegalArgumentException e) {
            showError("Input error", e.getMessage());
        } catch (NoResultException ex) {
            showError("Error with update", String.format("Cannot update user with email: %s, because it does not exist.", email));
        }
    }

    @FXML
    private void addEmployees() {
        try {
            userController.addUser(txtEmail.getText(),txtPassword.getText(), txtFirstName.getText(), txtName.getText(), txtTelephone.getText(), choiceBoxFunction.getValue(), txtStreet.getText(), Integer.parseInt(txtNumber.getText()), txtBox.getText(), txtCity.getText(), txtPostalCode.getText(), txtCountry.getText(), userController.getSupplier());
            showInfo("Employee created succesfully", String.format("created user %s", txtEmail.getText()));
        } catch (UserAlreadyExistsException e) {
            showError("Email already exists", String.format("There already exists a user with the email address of: %s", txtEmail.getText()));
        } catch (NumberFormatException e) {
            showError("Invalid house number", "Please insert a valid house number");
        }
    }

    @FXML
    private void clearFields() {
        txtName.clear();
        txtFirstName.clear();
        txtTelephone.clear();
        txtPassword.clear();
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