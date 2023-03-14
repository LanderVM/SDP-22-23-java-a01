package application;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import domain.ContactPerson;
import domain.ContactPersonSupplier;
import domain.Order;
import domain.OrderController;
import domain.Packaging;
import domain.Product;
import domain.Status;
import domain.Supplier;
import domain.SupplierController;
import domain.TrackingCodeDetails;
import domain.TransportService;
import domain.TransportServiceController;
import domain.User;
import domain.UserController;
import domain.VerificationType;
import util.FXStageUtil;
import gui.controller.LoginScreenController;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import persistence.ContactPersonSupplierJPADao;
import persistence.OrderJPADao;
import persistence.SupplierJPADao;
import persistence.TransportServiceJPADao;
import persistence.UserJPADao;
import util.JPAUtil;

public class StartUp extends Application {

    public static void main(String[] args) {
        seedDatabase();
        launch(args);
    }

    EntityManager entityManager;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXStageUtil.init(primaryStage);

            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        	OrderJPADao orderJPADao = new OrderJPADao(entityManager);
        	UserJPADao userJPADao = new UserJPADao(entityManager);
        	TransportServiceJPADao transportServiceJPADao = new TransportServiceJPADao(entityManager);
        	SupplierJPADao supplierJPADao = new SupplierJPADao(entityManager);
        	ContactPersonSupplierJPADao contactPersonSupplierJPADao = new ContactPersonSupplierJPADao(entityManager);

            LoginScreenController root = new LoginScreenController(
            		new OrderController(orderJPADao, userJPADao),
            		new UserController(userJPADao),
            		new TransportServiceController(transportServiceJPADao),
            		new SupplierController(supplierJPADao, orderJPADao, contactPersonSupplierJPADao));
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
        
        Supplier s1 = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , "0426343211", "/images/testImg.jpg");
        Supplier s2 = new Supplier("Jan INC","jan@mail.com","Janstraat 12 9000 Aalst", "0456443212", "/images/testImg.jpg");
        userManager.persist(s1);
        userManager.persist(s2);
        
        User admin = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", s1);
        User warehouseman = new User("testMagazijnier@mail.com", "testMagazijnier", false, "Tessa", "Magazijnier", s2);

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

        ContactPerson bpostPerson1 = new ContactPerson("een@bpost.be", "499334455");
        ContactPerson bpostPerson2 = new ContactPerson("twe@bpost.be", "479554433");
        ContactPerson postnlPerson1 = new ContactPerson("postnl@post.nl", "899321480");

        TransportService bpost = new TransportService("bpost", List.of(bpostPerson1, bpostPerson2), bpostDetails, true);
        TransportService postnl = new TransportService("postnl", List.of(postnlPerson1), postnlDetails, true);

        Order order1 = new Order( new Date(),"Honkstraat 33 Lokeren",List.of(product1, product1, product1, product1, product2, product2), Status.POSTED, postnl, Packaging.MEDIUM, s1,s2,new BigDecimal("3.00"));
        Order order2 = new Order( new Date(),"Bellelaan 12 Haaltert",List.of(product3, product4, product4, product4, product5, product5), Status.DELIVERED, bpost, Packaging.CUSTOM,s2,s1, new BigDecimal("24.70"));
        Order order3 = new Order( new Date(),"Doodskoplaan 73 Gent",List.of(product1, product3, product3, product4, product4, product5), Status.DISPATCHED, postnl, Packaging.SMALL,s1,s2, new BigDecimal("23.80"));
        Order order4 = new Order( new Date(),"Bekerstraat 66 Bilzen",List.of(product1, product1, product3, product4, product5, product5), Status.POSTED, bpost, Packaging.LARGE,s2,s1, new BigDecimal("21.20"));
        
        ContactPersonSupplier contactPersonSupplier1 =new ContactPersonSupplier("Jan Jaap","jan.jaap@gmail.com",s1);
        ContactPersonSupplier contactPersonSupplier2 =new ContactPersonSupplier("Bert Bratwurst","bert.bratwurst@gmail.com",s1);
        ContactPersonSupplier contactPersonSupplier3 =new ContactPersonSupplier("Erik Tanner","erik.tanner@gmail.com",s2);
        ContactPersonSupplier contactPersonSupplier4 =new ContactPersonSupplier("Elke Daems","elke.daems@gmail.com",s2);
        
        
        List<ContactPersonSupplier> listContactPersonSupplier1 = List.of(contactPersonSupplier1,contactPersonSupplier2);
        List<ContactPersonSupplier> listContactPersonSupplier2 = List.of(contactPersonSupplier3,contactPersonSupplier4);
        
        s1.setContactPersons(listContactPersonSupplier1);
        s2.setContactPersons(listContactPersonSupplier2);
        
        List<Order> l1 = List.of(order1, order3);
        List<Order> l2 = List.of(order2,order4);
        s1.setOrdersAsSupplier(l1);
        s1.setOrdersAsCustomer(l2);
        s2.setOrdersAsSupplier(l2);
        s2.setOrdersAsCustomer(l1);

        EntityManager orderManager = JPAUtil.getEntityManagerFactory().createEntityManager();
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
        orderManager.persist(order3);
        orderManager.persist(order4);

        orderManager.getTransaction().commit();
        orderManager.close();
    }
}