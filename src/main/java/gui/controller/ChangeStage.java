package gui.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeStage {

	// int width = (int) Screen.getPrimary().getBounds().getWidth();
	// int height = (int) Screen.getPrimary().getBounds().getHeight();

	public static void change(Parent current, FXMLLoader loader, Parent controller, String title) {
		loader.setRoot(controller);
		loader.setController(controller);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(controller);
		Stage stage = (Stage) current.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}

	public static void change(Parent current, FXMLLoader loader, Parent controller, String title, int width, int height) {
		loader.setRoot(controller);
		loader.setController(controller);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(controller);
		Stage stage = (Stage) current.getScene().getWindow();
		stage.setScene(scene);
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setTitle(title);
		stage.show();

	}
}
