package gui.controller;

import java.math.BigDecimal;

import domain.Packaging;
import domain.PackagingController;
import domain.PackagingType;
import domain.UserController;
import gui.view.PackagingDTO;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.FXStageUtil;

public class PackagingOverviewController {
	@FXML
	private Label lblUser;
	@FXML
	private TableView<Packaging> tblBoxes;
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
	private TextArea txtHeigth;
	@FXML
	private TextArea txtPrice;	
	@FXML
	private CheckBox chkboxIsActive;
	@FXML
	private ChoiceBox<String> choiceBoxType;

	private final UserController userController;
	private PackagingController packagingController;
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

		//refreshPackagingTable();
		
		choiceBoxType.setItems(PackagingController.getPackagingTypesObservableList());
		
		tblBoxes.getSelectionModel().selectedItemProperty()
		.addListener((observableValue, oldBox, newBox) -> {
			packagingId = newBox.getPackagingId();
		});
	}
	public void refreshPackagingTable() {
		tblBoxes.setItems(packagingController.getPackagingList());
	}

	@FXML
	private void showEmployees() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/EmployeesOverview.fxml"), "Employees");
	}

	@FXML
	private void showTransportservices() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/TransportServiceOverview.fxml"),
				"Transport Services");
	}

	@FXML
	private void addBox() {
		packagingController.addPackaging(txtName.getText(), Double.parseDouble(txtWidth.getText()),
				Double.parseDouble(txtHeigth.getText()), Double.parseDouble(txtLength.getText()),
				Double.parseDouble(txtPrice.getText()), choiceBoxType.getSelectionModel().getSelectedItem(),
				chkboxIsActive.isSelected());
	}

	@FXML
	private void deleteBox() {
		packagingController.deletePackaging(packagingId);
	}

	@FXML
	private void logOut() {
		FXStageUtil.setScene(PackagingOverviewController.class.getResource("/gui/LoginScreen.fxml"), "Log In");
	}

}
