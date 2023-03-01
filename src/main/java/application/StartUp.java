package application;

import domain.*;
import gui.LoginScreenController;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import persistence.OrderJPADao;
import util.JPAUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class StartUp extends Application {

    public static void main(String[] args) {
//        seedDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            LoginScreenController root = new LoginScreenController(new OrderController(new OrderJPADao(JPAUtil.getOrdersEntityManagerFactory().createEntityManager())), new UserController());
            Scene scene = new Scene(root, 600, 300);
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
        Product p1 = new Product("test_product een", 1.0);
        Product p2 = new Product("test_product twee", 2.0);
        Product p3 = new Product("test_product drie", 4.5);
        Product p4 = new Product("test_product vier", 8.9);
        Product p5 = new Product("test_product vijf", 11.30);

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

        Order o1 = new Order("Tim CO", "Tim", "tim@mail.com", "Timlaan 24 1000 Brussel", new Date(), List.of(p1, p2), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM);
        Order o2 = new Order("Jan INC", "Jan", "jan@mail.com", "Janstraat 12 9000 Aalst", new Date(), List.of(p3, p4, p5), Status.POSTED, TransportService.BPOST, Packaging.CUSTOM);

        EntityManager orderManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
        orderManager.getTransaction().begin();
        orderManager.persist(o1);
        orderManager.persist(o2);
        orderManager.getTransaction().commit();
        orderManager.close();

    }
}