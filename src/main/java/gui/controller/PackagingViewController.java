package gui.controller;

import java.math.BigDecimal;

import domain.PackagingController;
import domain.PackagingType;
import domain.UserController;
import gui.view.PackagingDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import util.FXStageUtil;

public class PackagingViewController {
	@FXML
	private Label lblUser;
    @FXML
	private TableView<PackagingDTO> tblBoxes;
	@FXML
	private TableColumn<PackagingDTO, String> tblBoxesClmName;
	@FXML
	private TableColumn<PackagingDTO, PackagingType> tblBoxesClmType;
	@FXML
	private TableColumn<PackagingDTO, String> tblBoxesClmMeasurements;
	@FXML
	private TableColumn<PackagingDTO, BigDecimal> tblBoxesClmPrice;
	@FXML
	private TableColumn<PackagingDTO, String> tblBoxesClmActive;
	@FXML
	private TextArea txtName;
	@FXML
	private TextArea txtWidth;
	@FXML
	private TextArea txtLength;
	@FXML
	private TextArea txtHeight;
	@FXML
	private TextArea txtPrice;
	@FXML
	private CheckBox chkIsActive;
	@FXML
	private ChoiceBox<String> choiceBoxType;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnAdd;

	private final UserController userController;
	private final PackagingController packagingController;

    public PackagingViewController(UserController userController, PackagingController packagingController) {
		this.userController = userController;
		this.packagingController = packagingController;
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

		// Table Boxes

		tblBoxesClmName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblBoxesClmType.setCellValueFactory(cellData -> cellData.getValue().packagingTypeProperty());
		tblBoxesClmMeasurements.setCellValueFactory(cellData -> cellData.getValue().dimensionsProperty());
		tblBoxesClmPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
		tblBoxesClmActive.setCellValueFactory(cellData -> cellData.getValue().activeProperty());

        ObservableList<PackagingDTO> packagingList = packagingController.getPackagingList();
		tblBoxes.setItems(packagingList);

		choiceBoxType.setItems(PackagingController.getPackagingTypesObservableList());

		tblBoxes.getSelectionModel().selectedItemProperty().addListener((observableValue, oldPackaging, newPackaging) -> {
			if (newPackaging == null)
				return;
            txtName.setText(newPackaging.getName());
            choiceBoxType.setValue(newPackaging.getPackagingType().toString());
            txtWidth.setText(newPackaging.getWidth().toString());
            txtHeight.setText(newPackaging.getHeight().toString());
            txtLength.setText(newPackaging.getLength().toString());
            txtPrice.setText(newPackaging.getPrice().toString());
            chkIsActive.setSelected(newPackaging.isActive());
				}
		);
	}

	@FXML
	private void showEmployees() {
		FXStageUtil.setScene(PackagingViewController.class.getResource("/gui/EmployeeView.fxml"), "Employees");
	}

	@FXML
	private void showCarrier() {
		FXStageUtil.setScene(PackagingViewController.class.getResource("/gui/CarrierView.fxml"), "Carriers");
	}

	@FXML
	private void addPackaging() {
        try {
            packagingController.addPackaging(txtName.getText(), Double.parseDouble(txtWidth.getText()),
                    Double.parseDouble(txtHeight.getText()), Double.parseDouble(txtLength.getText()),
                    Double.parseDouble(txtPrice.getText()), choiceBoxType.getSelectionModel().getSelectedItem(),
                    chkIsActive.isSelected());
            showAlert("Successful", "Packaging has been added.", AlertType.INFORMATION);
        } catch (IllegalArgumentException illegalArgumentException) {
            String message;
            if (illegalArgumentException.getMessage().startsWith("For input string"))
                message = "Invalid input: " + illegalArgumentException.getMessage().substring(17);
            else if (illegalArgumentException.getMessage().startsWith("empty String"))
                message = "Dimensions and Price are required!";
            else
                message = illegalArgumentException.getMessage();
            showAlert("Error", message, AlertType.ERROR);
        }
	}

	@FXML
	private void updatePackaging() {
		try {
			packagingController.updatePackaging(tblBoxes.getSelectionModel().getSelectedItem().getPackagingId(), txtName.getText(), Double.parseDouble(txtWidth.getText()),
					Double.parseDouble(txtHeight.getText()), Double.parseDouble(txtLength.getText()),
					Double.parseDouble(txtPrice.getText()), choiceBoxType.getSelectionModel().getSelectedItem(),
					chkIsActive.isSelected());
			showAlert("Successful", "Packaging has been updated.", AlertType.INFORMATION);
		} catch (IllegalArgumentException illegalArgumentException) {
            String message;
            if (illegalArgumentException.getMessage().startsWith("For input string"))
                message = "Invalid input: " + illegalArgumentException.getMessage().substring(17);
            else if (illegalArgumentException.getMessage().startsWith("empty String"))
                message = "Dimensions and Price are required!";
            else
                message = illegalArgumentException.getMessage();
            showAlert("Error", message, AlertType.ERROR);
		}
	}

	@FXML
	private void logOut() {
		FXStageUtil.setScene(PackagingViewController.class.getResource("/gui/LoginView.fxml"), "Log In");
	}

}
