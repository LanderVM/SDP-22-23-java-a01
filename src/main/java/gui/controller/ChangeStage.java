package gui.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeStage {
	private static Stage stage;
	
	public ChangeStage(Stage primaryStage) {
		stage = primaryStage;
	}

	public static void change(Parent current, FXMLLoader loader, Parent controller, String title) {
		int width = (int) stage.getWidth();
		int height = (int) stage.getHeight();
		change(current, loader, controller, title, width, height);
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
		stage = (Stage) current.getScene().getWindow();
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();

	}
}
