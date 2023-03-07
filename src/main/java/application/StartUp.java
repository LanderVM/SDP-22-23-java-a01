package application;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import domain.*;
import gui.controller.LoginScreenController;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import persistence.OrderJPADao;
import persistence.UserJPADao;
import util.JPAUtil;

public class StartUp extends Application {

    public static void main(String[] args) {
        seedDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            LoginScreenController root = new LoginScreenController(new OrderController(new OrderJPADao(JPAUtil.getOrdersEntityManagerFactory().createEntityManager())), new UserController(new UserJPADao(JPAUtil.getUserEntityManagerFactory().createEntityManager())));
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setResizable(true);
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(StartUp.class.getResourceAsStream("/Images/LogoDelaware.png"))));
            primaryStage.setTitle("Log In");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void seedDatabase() {
        User admin = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin");
        User warehouseman = new User("testMagazijnier@mail.com", "testMagazijnier", false, "Tessa", "Magazijnier");

        EntityManager userManager = JPAUtil.getUserEntityManagerFactory().createEntityManager();
        userManager.getTransaction().begin();

        // Users
        userManager.persist(admin);
        userManager.persist(warehouseman);

        userManager.getTransaction().commit();
        userManager.close();

        Product product1 = new Product("test_product een", new BigDecimal("1.00"));
        Product product2 = new Product("test_product twee", new BigDecimal("2.00"));
        Product product3 = new Product("test_product drie", new BigDecimal("4.50"));
        Product product4 = new Product("test_product vier", new BigDecimal("8.90"));
        Product product5 = new Product("test_product vijf", new BigDecimal("11.30"));

        TrackingCodeDetails bpostDetails = new TrackingCodeDetails(10, true, "32", VerificationType.POST_CODE);
        TrackingCodeDetails postnlDetails = new TrackingCodeDetails(13, false, "testprefix", VerificationType.ORDER_ID);

        ContactPerson bpostPerson1 = new ContactPerson("een@bpost.be", 499334455);
        ContactPerson bpostPerson2 = new ContactPerson("twe@bpost.be", 479554433);
        ContactPerson postnlPerson1 = new ContactPerson("postnl@post.nl", 899321480);

        NewTransportService bpost = new NewTransportService("bpost", List.of(), bpostDetails, true);
        NewTransportService postnl = new NewTransportService("postnl", List.of(), postnlDetails, true);

        Order order1 = new Order("Tim CO", "Tim", "tim@mail.com", "Timlaan 24 1000 Brussel", new Date(), List.of(product1, product2), Status.POSTED, postnl, Packaging.MEDIUM, new BigDecimal("3.00"));
        Order order2 = new Order("Jan INC", "Jan", "jan@mail.com", "Janstraat 12 9000 Aalst", new Date(), List.of(product3, product4, product5), Status.POSTED, bpost, Packaging.CUSTOM, new BigDecimal("24.70"));

        EntityManager orderManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
        orderManager.getTransaction().begin();

        // Products
        orderManager.persist(product1);
        orderManager.persist(product2);
        orderManager.persist(product3);
        orderManager.persist(product4);
        orderManager.persist(product5);

        // TrackingService
        orderManager.persist(bpostPerson1);
        orderManager.persist(bpostPerson2);
        orderManager.persist(postnlPerson1);
        orderManager.persist(bpostDetails);
        orderManager.persist(postnlDetails);
        orderManager.persist(bpost);
        orderManager.persist(postnl);

        // Orders
        orderManager.persist(order1);
        orderManager.persist(order2);

        orderManager.getTransaction().commit();
        orderManager.close();
    }
}