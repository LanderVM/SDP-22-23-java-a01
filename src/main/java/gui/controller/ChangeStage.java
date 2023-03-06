package gui.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeStage {

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
}
