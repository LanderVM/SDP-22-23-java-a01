package application;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import domain.*;

import persistence.*;
import persistence.impl.*;
import util.FXStageUtil;
import gui.controller.LoginScreenController;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.JPAUtil;

public class StartUp extends Application {

    public static void main(String[] args) {
        seedDatabase();
        launch(args);
    }

    EntityManager entityManager;
    OrderDao orderDao;
    SupplierDao supplierDao;
    UserDao userDao;
    ContactPersonSupplierDao contactPersonSupplierDao;
    TransportServiceDao transportServiceDao;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXStageUtil.init(primaryStage);

            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            orderDao = new OrderDaoJpa(entityManager);
            supplierDao = new SupplierDaoJpa(entityManager);
            userDao = new UserDaoJpa(entityManager);
            contactPersonSupplierDao = new ContactPersonSupplierDaoJpa(entityManager);
            transportServiceDao = new TransportServiceDaoJpa(entityManager);

            LoginScreenController root = new LoginScreenController(
                    new OrderController(orderDao,transportServiceDao),
                    new UserController(userDao),
                    new TransportServiceController(transportServiceDao),
                    new SupplierController(supplierDao, orderDao, contactPersonSupplierDao));

    		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/LoginScreen.fxml"));
    		loader.setController(root);
    		loader.setRoot(root);
    		try {
    			loader.load();
    		} catch (IOException e) {
    			throw new RuntimeException(e);
    		}

            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setResizable(true);
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(StartUp.class.getResourceAsStream("/Images/LogoDelaware.png"))));
            primaryStage.setTitle("Log In");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        entityManager.close();
        JPAUtil.getEntityManagerFactory().close();
    }

    public static void seedDatabase() {
        EntityManager userManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        userManager.getTransaction().begin();

     // Aanmaken Objecten
        Supplier s1 = new Supplier("Tim CO", "tim@mail.com", "Timlaan 24 1000 Brussel", "0426343211", "/images/testImg.jpg");
        Supplier s2 = new Supplier("Jan INC", "jan@mail.com", "Janstraat 12 9000 Aalst", "0456443212", "/images/testImg.jpg");

        TrackingCodeDetails bpostDetails = new TrackingCodeDetails(10, true, "32", VerificationType.POST_CODE);
        TrackingCodeDetails postnlDetails = new TrackingCodeDetails(13, false, "testprefix", VerificationType.ORDER_ID);

        ContactPerson bpostPerson1 = new ContactPerson("een@bpost.be", "499334455");
        ContactPerson bpostPerson2 = new ContactPerson("twe@bpost.be", "479554433");
        ContactPerson postnlPerson1 = new ContactPerson("postnl@post.nl", "899321480");

        TransportService bpost = new TransportService("bpost", List.of(bpostPerson1, bpostPerson2), bpostDetails, true);
        TransportService postnl = new TransportService("postnl", List.of(postnlPerson1), postnlDetails, true);
        
        Product product1 = new Product("test_product een", new BigDecimal("1.00"));
        Product product2 = new Product("test_product twee", new BigDecimal("2.00"));
        Product product3 = new Product("test_product drie", new BigDecimal("4.50"));
        Product product4 = new Product("test_product vier", new BigDecimal("8.90"));
        Product product5 = new Product("test_product vijf", new BigDecimal("11.30"));
        
        Order order1 = new Order(LocalDate.now().minusDays(3),"Honkstraat 33 Lokeren",List.of(product1, product1, product1, product1, product2, product2), Status.POSTED, null, PackagingType.STANDARD, s1,s2,new BigDecimal("3.00"));
        Order order2 = new Order(LocalDate.now().minusDays(2),"Bellelaan 12 Haaltert",List.of(product3, product4, product4, product4, product5, product5), Status.DELIVERED, bpost, PackagingType.CUSTOM,s2,s1, new BigDecimal("24.70"));
        Order order3 = new Order(LocalDate.now().minusDays(1),"Doodskoplaan 73 Gent",List.of(product1, product3, product3, product4, product4, product5), Status.DISPATCHED, postnl, PackagingType.STANDARD,s1,s2, new BigDecimal("23.80"));
        Order order4 = new Order(LocalDate.now(),"Bekerstraat 66 Bilzen",List.of(product1, product1, product3, product4, product5, product5), Status.POSTED, null, PackagingType.CUSTOM,s2,s1, new BigDecimal("21.20"));
        
        
        order2.generateTrackingCode();
        order3.generateTrackingCode();

        Notification postedNotification = new Notification(order1);
        Notification processedNotification = new Notification(order2, LocalDate.of(2023, 3, 12));
        Notification deliveredNotification = new Notification(order2, LocalDate.of(2023, 3, 14));

        order1.addNotification(postedNotification);
        order1.addNotification(processedNotification);
        order2.addNotification(deliveredNotification);

        ContactPersonSupplier contactPersonSupplier1 =new ContactPersonSupplier("Jan Jaap","jan.jaap@gmail.com",s1);
        ContactPersonSupplier contactPersonSupplier2 =new ContactPersonSupplier("Bert Bratwurst","bert.bratwurst@gmail.com",s1);
        ContactPersonSupplier contactPersonSupplier3 =new ContactPersonSupplier("Erik Tanner","erik.tanner@gmail.com",s2);
        ContactPersonSupplier contactPersonSupplier4 =new ContactPersonSupplier("Elke Daems","elke.daems@gmail.com",s2);
        
        User admin = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", s1);
        User warehouseman = new User("testMagazijnier@mail.com", "testMagazijnier", false, "Tessa", "Magazijnier", s2);
        
        List<ContactPersonSupplier> listContactPersonSupplier1 = List.of(contactPersonSupplier1,contactPersonSupplier2);
        List<ContactPersonSupplier> listContactPersonSupplier2 = List.of(contactPersonSupplier3,contactPersonSupplier4);
        
        s1.setContactPersons(listContactPersonSupplier1);
        s2.setContactPersons(listContactPersonSupplier2);
        
        List<Order> l1 = List.of(order1, order3);
        List<Order> l2 = List.of(order2, order4);
        
        s1.setOrdersAsCustomer(l1);     
        s2.setOrdersAsCustomer(l2);
        
        s1.setOrdersAsSupplier(l2);     
        s2.setOrdersAsSupplier(l1);
        
        order1.setSupplier(s2);
        order2.setSupplier(s1);
        order3.setSupplier(s2);
        order4.setSupplier(s1);
        
        order1.setCustomer(s1);
        order2.setCustomer(s2);
        order3.setCustomer(s1);
        order4.setCustomer(s2);
        
        //Package small = new Package("small", Packaging.SMALL, new BigDecimal(1.0), new BigDecimal(5.0), new BigDecimal(1.0), new BigDecimal(5.0));
        //Package medium = new Package("medium", Packaging.MEDIUM, new BigDecimal(5.0), new BigDecimal(10.0), new BigDecimal(2.0), new BigDecimal(10.0));
        //Package large = new Package("large", Packaging.LARGE, new BigDecimal(10.0), new BigDecimal(15.0), new BigDecimal(3.0), new BigDecimal(15.0));
        //Package custom = new Package("custom", Packaging.CUSTOM, new BigDecimal(7.5), new BigDecimal(15.0), new BigDecimal(1.5), new BigDecimal(5.0));
        
        // Persisteren Objecten in de db
        // Products
        userManager.persist(product1);
        userManager.persist(product2);
        userManager.persist(product3);
        userManager.persist(product4);
        userManager.persist(product5);

        // TrackingService
        userManager.persist(bpostPerson1);
        userManager.persist(bpostPerson2);
        userManager.persist(postnlPerson1);
        userManager.persist(bpostDetails);
        userManager.persist(postnlDetails);
        
        userManager.persist(bpost);
        userManager.persist(postnl);
        
        userManager.persist(order1);
        userManager.persist(order2);
        userManager.persist(order3);
        userManager.persist(order4);
        
        userManager.persist(s1);
        userManager.persist(s2);

        userManager.persist(postedNotification);
        userManager.persist(processedNotification);
        userManager.persist(deliveredNotification);
        
//        userManager.persist(small);
//        userManager.persist(medium);
//        userManager.persist(large);
//        userManager.persist(custom);
        
        userManager.persist(admin);
        userManager.persist(warehouseman);

        userManager.getTransaction().commit();
        userManager.close();
    }
}