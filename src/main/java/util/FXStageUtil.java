package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXStageUtil {
	private static Stage stage;

	public static void init(Stage stage) {
		FXStageUtil.stage = stage;
	}

	public static void change(Parent current, FXMLLoader loader, Object controller, String title) {
		if (stage == null)
			throw new IllegalStateException("Primary stage must be initialized before running this method");

		int width = (int) stage.getWidth();
		int height = (int) stage.getHeight();

		loader.setController(controller);
		try {
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage = (Stage) current.getScene().getWindow();
			stage.setWidth(width);
			stage.setHeight(height);
			stage.setTitle(title);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
