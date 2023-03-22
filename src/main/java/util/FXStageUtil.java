package util;

import java.io.IOException;

import gui.controller.LoginScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXStageUtil {
    private static Stage stage;
    private static int width;
    private static int height;

    public static void init(Stage stage) {
        FXStageUtil.stage = stage;
        width = (int) stage.getWidth();
        height = (int) stage.getHeight();
    }

	public static void change(FXMLLoader loader, Object controller, String title) {
		if (stage == null)
			throw new IllegalStateException("Primary stage must be initialized before running this method");

		if (controller instanceof LoginScreenController) {
			loader.setRoot(controller);
		}
		try {
            loader.setController(controller);
            Parent root = loader.load();
			Scene scene = new Scene(root);
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
