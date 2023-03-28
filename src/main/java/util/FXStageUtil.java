package util;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import application.StartUp;
import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import gui.controller.LoginScreenController;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import persistence.*;
import persistence.impl.*;

public class FXStageUtil {
    private static Stage stage;
    private static int width;
    private static int height;

	public static EntityManager entityManager;
	private static OrderDao orderDao;
	private static SupplierDao supplierDao;
	private static UserDao userDao;
	private static ContactPersonSupplierDao contactPersonSupplierDao;
	private static TransportServiceDao transportServiceDao;
	private static FXMLLoader loader;

    public static void init(Stage stage) {
        FXStageUtil.stage = stage;
        width = (int) stage.getWidth();
        height = (int) stage.getHeight();
    }

	private static void setLoader() {
		loader = new FXMLLoader();
		loader.setControllerFactory(controller -> {
			if (controller == LoginScreenController.class) {
				return new LoginScreenController(
						new OrderController(orderDao, transportServiceDao),
						new UserController(userDao),
						new TransportServiceController(transportServiceDao, supplierDao),
						new SupplierController(supplierDao, orderDao, contactPersonSupplierDao));
			} else {
				try {
					return controller.getConstructor().newInstance();
				} catch (Exception exc) {
					throw new RuntimeException(exc);
				}
			}
		});
	}

	public static void newStage(Stage primaryStage, URL location) {
		FXStageUtil.init(primaryStage);

		entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		orderDao = new OrderDaoJpa(entityManager);
		supplierDao = new SupplierDaoJpa(entityManager);
		userDao = new UserDaoJpa(entityManager);
		contactPersonSupplierDao = new ContactPersonSupplierDaoJpa(entityManager);
		transportServiceDao = new TransportServiceDaoJpa(entityManager);


		if (loader == null)
			setLoader();

		try {
			loader.setLocation(location);
			primaryStage.setResizable(true);
			primaryStage.getIcons().add(new Image(Objects.requireNonNull(StartUp.class.getResourceAsStream("/Images/LogoDelaware.png"))));
			primaryStage.setTitle("Log In");
			primaryStage.setMaximized(true);
			primaryStage.setScene(new Scene(loader.load()));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
