package application;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
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
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(StartUp.class.getResourceAsStream("/Images/LogoDelaware.png"))));
            primaryStage.setTitle("Log In");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void seedDatabase() {
        Product p1 = new Product("test_product een", new BigDecimal("1.00"));
        Product p2 = new Product("test_product twee", new BigDecimal("2.00"));
        Product p3 = new Product("test_product drie", new BigDecimal("4.50"));
        Product p4 = new Product("test_product vier", new BigDecimal("8.90"));
        Product p5 = new Product("test_product vijf", new BigDecimal("11.30"));

        User admin = new User("testAdmin@mail.com", "testAdmin", true);
        User warehouseman = new User("testMagazijnier@mail.com", "testMagazijnier", false);

        EntityManager entityManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        entityManager.persist(p4);
        entityManager.persist(p5);
        entityManager.getTransaction().commit();
        entityManager.close();

        EntityManager userManager = JPAUtil.getUserEntityManagerFactory().createEntityManager();
        userManager.getTransaction().begin();
        userManager.persist(admin);
        userManager.persist(warehouseman);
        userManager.getTransaction().commit();
        userManager.close();

        Supplier s1 = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , 426343211, getFile(), List.of());
        Supplier s2 = new Supplier("Jan INC","jan@mail.com","Janstraat 12 9000 Aalst", 456443212,getFile(), List.of());
        
        Order o1 = new Order( new Date(), List.of(p1, p2), Status.POSTED, TransportService.POSTNL, Packaging.MEDIUM,s1,s2);
        Order o2 = new Order( new Date(), List.of(p3, p4, p5), Status.POSTED, TransportService.BPOST, Packaging.CUSTOM,s2,s1);
        
        List<Order> l1 = new ArrayList<>();
        l1.add(o1);
        List<Order> l2 = new ArrayList<>();
        l2.add(o2);
        s1.setOrders(l1);
        s2.setOrders(l2);

        EntityManager orderManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
        orderManager.getTransaction().begin();
        orderManager.persist(o1);
        orderManager.persist(o2);
        orderManager.persist(s1);
        orderManager.persist(s2);
        orderManager.getTransaction().commit();
        orderManager.close();
    }

    private static byte[] getFile() {
        byte[] fileContent = null;
        try {
            File fi = new File(Objects.requireNonNull(StartUp.class.getResource("/gui/HomeAdmin.fxml")).toURI());
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
        return fileContent;
    }
}