package application;

import domain.DomainController;
import domain.Product;
import domain.User;
import domain.UserController;
import gui.LoginScreenController;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.JPAUtil;

import java.util.Objects;

public class StartUp extends Application {

    public static void main(String[] args) {
        seedDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            LoginScreenController root = new LoginScreenController(new DomainController(), new UserController());
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

    private static void seedDatabase() {
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
    }
}