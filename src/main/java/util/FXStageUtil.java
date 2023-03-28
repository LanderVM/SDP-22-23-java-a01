package util;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import application.StartUp;
import domain.OrderController;
import domain.PackagingController;
import domain.SupplierController;
import domain.TransportServiceController;
import domain.UserController;
import gui.controller.CustomersOverviewController;
import gui.controller.EmployeesOverviewController;
import gui.controller.LoginScreenController;
import gui.controller.OrdersOverviewController;
import gui.controller.PackagingOverviewController;
import gui.controller.TransportServiceOverviewController;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import persistence.ContactPersonSupplierDao;
import persistence.OrderDao;
import persistence.PackagingDao;
import persistence.SupplierDao;
import persistence.TransportServiceDao;
import persistence.UserDao;
import persistence.impl.ContactPersonSupplierDaoJpa;
import persistence.impl.OrderDaoJpa;
import persistence.impl.PackagingDaoJpa;
import persistence.impl.SupplierDaoJpa;
import persistence.impl.TransportServiceDaoJpa;
import persistence.impl.UserDaoJpa;

public class FXStageUtil {
    private static Stage stage;
    private static Scene scene;

    private static final EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    private static final OrderDao orderDao = new OrderDaoJpa(entityManager);
    private static final SupplierDao supplierDao = new SupplierDaoJpa(entityManager);
    private static final UserDao userDao = new UserDaoJpa(entityManager);
    private static final ContactPersonSupplierDao contactPersonSupplierDao = new ContactPersonSupplierDaoJpa(entityManager);
    private static final TransportServiceDao transportServiceDao = new TransportServiceDaoJpa(entityManager);
    private static final PackagingDao packagingDao = new PackagingDaoJpa(entityManager);

    private static final OrderController orderController = new OrderController(orderDao, transportServiceDao);
    private static final UserController userController = new UserController(userDao);
    private static final TransportServiceController transportServiceController = new TransportServiceController(transportServiceDao, supplierDao);
    private static final SupplierController supplierController = new SupplierController(supplierDao, orderDao, contactPersonSupplierDao);
    private static final PackagingController packagingController = new PackagingController(packagingDao, userController);

    private static FXMLLoader loader;

    private static void setLoader(URL location) {
        loader = new FXMLLoader();
        loader.setControllerFactory(controller -> {
            if (controller == LoginScreenController.class)
                return new LoginScreenController(userController);
            if (controller == OrdersOverviewController.class)
                return new OrdersOverviewController(orderController, userController, transportServiceController);
            if (controller == CustomersOverviewController.class)
                return new CustomersOverviewController(userController, supplierController);
            if (controller == TransportServiceOverviewController.class)
                return new TransportServiceOverviewController(userController, transportServiceController);
            if (controller == EmployeesOverviewController.class)
                return new EmployeesOverviewController(userController);
            if (controller == PackagingOverviewController.class)
                return new PackagingOverviewController(userController,  packagingController);
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
            stage.setFullScreen(true);
            scene = new Scene(loader.load());
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

    public static void close() {
        entityManager.close();
        JPAUtil.getEntityManagerFactory().close();
    }
}
