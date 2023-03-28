package util;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import application.StartUp;
import domain.OrderController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import gui.controller.CustomersOverviewController;
import gui.controller.LoginScreenController;
import gui.controller.OrdersOverviewController;
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
    private static Scene scene;

    private static final EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    private static final OrderDao orderDao = new OrderDaoJpa(entityManager);
    private static final SupplierDao supplierDao = new SupplierDaoJpa(entityManager);
    private static final UserDao userDao = new UserDaoJpa(entityManager);
    private static final ContactPersonSupplierDao contactPersonSupplierDao = new ContactPersonSupplierDaoJpa(entityManager);
    private static final TransportServiceDao transportServiceDao = new TransportServiceDaoJpa(entityManager);

    private static final OrderController orderController = new OrderController(orderDao, transportServiceDao);
    private static final UserController userController = new UserController(userDao);
    private static final TransportServiceController transportServiceController = new TransportServiceController(transportServiceDao, supplierDao);
    private static final SupplierController supplierController = new SupplierController(supplierDao, orderDao, contactPersonSupplierDao);

    private static FXMLLoader loader;

    private static void setLoader(URL location) {
        loader = new FXMLLoader();
        loader.setControllerFactory(controller -> {
            if (controller == LoginScreenController.class)
                return new LoginScreenController(orderController, userController, transportServiceController, supplierController);
            if (controller == OrdersOverviewController.class)
                return new OrdersOverviewController(orderController, userController, transportServiceController);
            if (controller == CustomersOverviewController.class)
                return new CustomersOverviewController(userController, supplierController);
            else {
                try {
                    return controller.getConstructor().newInstance();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        });
        loader.setLocation(location);
    }

    public static void setStage(Stage primaryStage, URL location, String title) {
        try {
            setLoader(location);
            FXStageUtil.stage = primaryStage;
            stage.setResizable(true);
            stage.getIcons().add(new Image(Objects.requireNonNull(StartUp.class.getResourceAsStream("/Images/LogoDelaware.png"))));
            stage.setMaximized(true);
            scene = new Scene(loader.load());
            width = (int) stage.getWidth();
            height = (int) stage.getHeight();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setScene(URL location, String title) {
        if (stage == null || scene == null)
            throw new IllegalStateException("Primary stage must be initialized before running this method");
        try {
            setLoader(Objects.requireNonNull(location));
            scene.setRoot(loader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void change(FXMLLoader loader, Object controller, String title) {
        if (stage == null)
            throw new IllegalStateException("Primary stage must be initialized before running this method");
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

    public static void close() {
        entityManager.close();
        JPAUtil.getEntityManagerFactory().close();
    }
}
