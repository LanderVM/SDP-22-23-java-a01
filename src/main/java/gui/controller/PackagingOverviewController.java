package gui.controller;

import java.math.BigDecimal;

import domain.PackagingController;
import domain.PackagingType;
import domain.UserController;
import gui.view.PackagingDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.FXStageUtil;

public class PackagingOverviewController {
	@FXML
	private Label lblUser;
    private Alert alert;
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
	private TableColumn<PackagingDTO, Boolean> tblBoxesClmActive;
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
	private int packagingId = -1;

	public PackagingOverviewController(UserController userController, PackagingController packagingController) {
		this.userController = userController;
		this.packagingController = packagingController;
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

		tblBoxes.getSelectionModel().selectedItemProperty().addListener((observableValue, oldBox, newBox) -> {
			if (newBox == null)
				return;
			this.packagingId = newBox.getPackagingId();
		});
	}

	@FXML
	private void showEmployees() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/EmployeesOverview.fxml"), "Employees");
	}

	@FXML
	private void showTransportServices() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/TransportServiceOverview.fxml"),
				"Transport Services");
	}

	@FXML
	private void addPackaging() {
        try {
            packagingController.addPackaging(txtName.getText(), Double.parseDouble(txtWidth.getText()),
                    Double.parseDouble(txtHeight.getText()), Double.parseDouble(txtLength.getText()),
                    Double.parseDouble(txtPrice.getText()), choiceBoxType.getSelectionModel().getSelectedItem(),
                    chkIsActive.isSelected());
			// TODO alert wanneer successvol
        } catch (IllegalArgumentException illegalArgumentException) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unsuccessful");
            alert.setHeaderText(null);
            String message;
            if (illegalArgumentException.getMessage().startsWith("For input string")) {
                message = "Invalid input: " + illegalArgumentException.getMessage().substring(17);
            } else if (illegalArgumentException.getMessage().startsWith("empty String")) {
                message = "Dimensions and price are required!";
            } else {
                message = illegalArgumentException.getMessage();
            }
            alert.setContentText(message);
            alert.showAndWait();
        } // TODO extract deze catch naar aparte methode voor zowel addPackaging als updatePackaging
	}

	@FXML
	private void updatePackaging() {
		try {
			packagingController.updatePackaging(tblBoxes.getSelectionModel().getSelectedItem().getPackagingId(), txtName.getText(), Double.parseDouble(txtWidth.getText()),
					Double.parseDouble(txtHeight.getText()), Double.parseDouble(txtLength.getText()),
					Double.parseDouble(txtPrice.getText()), choiceBoxType.getSelectionModel().getSelectedItem(),
					chkIsActive.isSelected());
			// TODO alert wanneer successvol
		} catch (IllegalArgumentException illegalArgumentException) {
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Unsuccessful");
			alert.setHeaderText(null);
			String message;
			if (illegalArgumentException.getMessage().startsWith("For input string")) {
				message = "Invalid input: " + illegalArgumentException.getMessage().substring(17);
			} else if (illegalArgumentException.getMessage().startsWith("empty String")) {
				message = "Dimensions and price are required!";
			} else {
				message = illegalArgumentException.getMessage();
			}
			alert.setContentText(message);
			alert.showAndWait();
		}
	}

	@FXML
	private void logOut() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
	}

}
