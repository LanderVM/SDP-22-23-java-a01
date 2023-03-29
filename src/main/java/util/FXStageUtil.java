package util;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import application.StartUp;
import domain.OrderController;
import domain.PackagingController;
import domain.SupplierController;
import domain.CarrierController;
import domain.UserController;
import gui.controller.CustomerViewController;
import gui.controller.EmployeeViewController;
import gui.controller.LoginViewController;
import gui.controller.OrderViewController;
import gui.controller.PackagingViewController;
import gui.controller.CarrierViewController;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import persistence.ContactPersonSupplierDao;
import persistence.OrderDao;
import persistence.PackagingDao;
import persistence.SupplierDao;
import persistence.CarrierDao;
import persistence.UserDao;
import persistence.impl.ContactPersonSupplierDaoJpa;
import persistence.impl.OrderDaoJpa;
import persistence.impl.PackagingDaoJpa;
import persistence.impl.SupplierDaoJpa;
import persistence.impl.CarrierDaoJpa;
import persistence.impl.UserDaoJpa;

public class FXStageUtil {
    private static Stage stage;
    private static Scene scene;

    private static final EntityManager ENTITY_MANAGER = JPAUtil.getEntityManagerFactory().createEntityManager();
    private static final OrderDao ORDER_DAO_JPA = new OrderDaoJpa(ENTITY_MANAGER);
    private static final SupplierDao SUPPLIER_DAO_JPA = new SupplierDaoJpa(ENTITY_MANAGER);
    private static final UserDao USER_DAO_JPA = new UserDaoJpa(ENTITY_MANAGER);
    private static final ContactPersonSupplierDao CONTACT_PERSON_SUPPLIER_DAO_JPA = new ContactPersonSupplierDaoJpa(ENTITY_MANAGER);
    private static final CarrierDao CARRIER_DAO = new CarrierDaoJpa(ENTITY_MANAGER);
    private static final PackagingDao PACKAGING_DAO_JPA = new PackagingDaoJpa(ENTITY_MANAGER);

    private static final OrderController ORDER_CONTROLLER = new OrderController(ORDER_DAO_JPA, CARRIER_DAO);
    private static final UserController USER_CONTROLLER = new UserController(USER_DAO_JPA);
    private static final CarrierController CARRIER_CONTROLLER = new CarrierController(CARRIER_DAO, SUPPLIER_DAO_JPA);
    private static final SupplierController SUPPLIER_CONTROLLER = new SupplierController(SUPPLIER_DAO_JPA, ORDER_DAO_JPA, CONTACT_PERSON_SUPPLIER_DAO_JPA);
    private static final PackagingController PACKAGING_CONTROLLER = new PackagingController(PACKAGING_DAO_JPA, USER_CONTROLLER);

    private static FXMLLoader loader;

    private static void setLoader(URL location) {
        loader = new FXMLLoader();
        loader.setControllerFactory(controller -> {
            if (controller == LoginViewController.class)
                return new LoginViewController(USER_CONTROLLER);
            if (controller == OrderViewController.class)
                return new OrderViewController(ORDER_CONTROLLER, USER_CONTROLLER, CARRIER_CONTROLLER);
            if (controller == CustomerViewController.class)
                return new CustomerViewController(USER_CONTROLLER, SUPPLIER_CONTROLLER);
            if (controller == CarrierViewController.class)
                return new CarrierViewController(USER_CONTROLLER, CARRIER_CONTROLLER);
            if (controller == EmployeeViewController.class)
                return new EmployeeViewController(USER_CONTROLLER);
            if (controller == PackagingViewController.class)
                return new PackagingViewController(USER_CONTROLLER, PACKAGING_CONTROLLER);
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
            stage.setHeight(900);
            stage.setWidth(1600);
            stage.setMaximized(true);
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
        ENTITY_MANAGER.close();
        JPAUtil.getEntityManagerFactory().close();
    }
}
