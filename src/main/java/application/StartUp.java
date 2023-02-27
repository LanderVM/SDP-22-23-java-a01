package application;
	
import domain.DomainController;
import gui.LoginScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartUp extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			LoginScreenController root = new LoginScreenController(new DomainController());
			Scene scene = new Scene(root, 600, 300);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setResizable(false);
            //primaryStage.getIcons().add(new Image(StartUp.class.getResourceAsStream("../resources/Images/LogoDelaware.png")));
            primaryStage.setTitle("Log In");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}