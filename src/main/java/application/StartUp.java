package application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import domain.*;

import util.FXStageUtil;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.stage.Stage;
import util.JPAUtil;

public class StartUp extends Application {

    public static void main(String[] args) {
        seedDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXStageUtil.setStage(primaryStage, StartUp.class.getResource("/gui/LoginView.fxml"), "Log In");
    }

    @Override
    public void stop() {
        FXStageUtil.close();
    }

    public static void seedDatabase() {
        EntityManager userManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        userManager.getTransaction().begin();

        /*
         *
         * Creating objects
         *
         */

        // Suppliers with TrackingService and User
        Supplier timCo = new Supplier("Tim CO", "tim@mail.com", "Timlaan 24 1000 Brussel", "0426343211", "/images/testImg.jpg");
        Supplier janInc = new Supplier("Jan INC", "jan@mail.com", "Janstraat 12 9000 Aalst", "0456443212", "/images/testImg.jpg");

        TrackingCodeDetails bpostDetails = new TrackingCodeDetails(10, true, "32", VerificationType.POST_CODE);
        TrackingCodeDetails postnlDetails = new TrackingCodeDetails(13, false, "testprefix", VerificationType.ORDER_ID);

        ContactPerson bpostPerson1 = new ContactPerson("een@bpost.be", "499334455");
        ContactPerson bpostPerson2 = new ContactPerson("twee@bpost.be", "479554433");
        ContactPerson bpostPerson3 = new ContactPerson("drie@bpost.be", "499334455");
        ContactPerson bpostPerson4 = new ContactPerson("vier@bpost.be", "479554433");
        ContactPerson postnlPerson1 = new ContactPerson("een@post.nl", "899321480");
        ContactPerson postnlPerson2 = new ContactPerson("twee@post.nl", "899321480");

        Carrier bpost1 = new Carrier("bpost", List.of(bpostPerson1, bpostPerson2), bpostDetails, timCo, true);
        Carrier postnl1 = new Carrier("postnl", List.of(postnlPerson1), postnlDetails, timCo, true);
        Carrier bpost2 = new Carrier("bpost", List.of(bpostPerson3, bpostPerson4), bpostDetails, janInc, true);
        Carrier postnl2 = new Carrier("postnl", List.of(postnlPerson2), postnlDetails, janInc, true);

        timCo.setCarriers(List.of(bpost1, postnl1));
        janInc.setCarriers(List.of(bpost2, postnl2));

        ContactPersonSupplier contactPersonSupplier1 = new ContactPersonSupplier("Jan Jaap", "jan.jaap@gmail.com", timCo);
        ContactPersonSupplier contactPersonSupplier2 = new ContactPersonSupplier("Bert Bratwurst", "bert.bratwurst@gmail.com", timCo);
        ContactPersonSupplier contactPersonSupplier3 = new ContactPersonSupplier("Erik Tanner", "erik.tanner@gmail.com", janInc);
        ContactPersonSupplier contactPersonSupplier4 = new ContactPersonSupplier("Elke Daems", "elke.daems@gmail.com", janInc);

        User admin = new User("testAdmin@mail.com", "testAdmin", true, "Test", "Admin", "02 70 25 25", "0470 25 25 25", "doeStreet", 23, "B2", "Aalst", "9300", "Belgium", timCo);
        User warehouseman = new User("testMagazijnier@mail.com", "testMagazijnier", false, "Tessa", "Magazijnier", "02 70 25 25", "0470 25 12 34", "gentStreet", 23, "A1", "Gent", "9000", "Belgium", janInc);

        List<ContactPersonSupplier> listContactPersonSupplier1 = List.of(contactPersonSupplier1, contactPersonSupplier2);
        List<ContactPersonSupplier> listContactPersonSupplier2 = List.of(contactPersonSupplier3, contactPersonSupplier4);

        timCo.setContactPersons(listContactPersonSupplier1);
        janInc.setContactPersons(listContactPersonSupplier2);

        // Orders with Products, Packaging and Notifications
        Product product1 = new Product("test_product een", new BigDecimal("1.00"));
        Product product2 = new Product("test_product twee", new BigDecimal("2.00"));
        Product product3 = new Product("test_product drie", new BigDecimal("4.50"));
        Product product4 = new Product("test_product vier", new BigDecimal("8.90"));
        Product product5 = new Product("test_product vijf", new BigDecimal("11.30"));

        Packaging timCoSmallStandard = new Packaging("Small", 2.0, 3.0, 2.0, 15.00, PackagingType.STANDARD, false, timCo);
        Packaging timCoBigCustom = new Packaging("Medium", 3.0, 4.5, 4.5, 30.00, PackagingType.CUSTOM, true, timCo);
        Packaging janIncMediumCustom = new Packaging("Medium", 4.0, 5.5, 6.6, 10.00, PackagingType.CUSTOM, true, janInc);
        Packaging janIncMediumCustomOld = new Packaging("MediumOld", 5.0, 4.5, 6.6, 13.50, PackagingType.STANDARD, false, janInc);

        Order order1 = new Order(LocalDate.now().minusDays(3), "Honkstraat 33 Lokeren", List.of(product1, product1, product1, product1, product2, product2), Status.POSTED, null, timCoBigCustom, timCo, janInc, new BigDecimal("3.00"));
        Order order2 = new Order(LocalDate.now().minusDays(2), "Bellelaan 12 Haaltert", List.of(product3, product4, product4, product4, product5, product5), Status.DELIVERED, bpost2, janIncMediumCustomOld, janInc, timCo, new BigDecimal("24.70"));
        order2.generateTrackingCode();
        Order order3 = new Order(LocalDate.now().minusDays(1), "Doodskoplaan 73 Gent", List.of(product1, product3, product3, product4, product4, product5), Status.DISPATCHED, postnl1, timCoSmallStandard, timCo, janInc, new BigDecimal("23.80"));
        order3.generateTrackingCode();
        Order order4 = new Order(LocalDate.now(), "Bekerstraat 66 Bilzen", List.of(product1, product1, product3, product4, product5, product5), Status.POSTED, null, janIncMediumCustom, janInc, timCo, new BigDecimal("21.20"));

        Notification postedNotification = new Notification(order1);
        Notification processedNotification = new Notification(order2, LocalDate.of(2023, 3, 12));
        Notification deliveredNotification = new Notification(order2, LocalDate.of(2023, 3, 14));


        order1.addNotification(postedNotification);
        order2.addNotification(processedNotification);
        order2.addNotification(deliveredNotification);

        List<Order> l1 = List.of(order1, order3);
        List<Order> l2 = List.of(order2, order4);

        timCo.setOrdersAsCustomer(l1);
        janInc.setOrdersAsCustomer(l2);

        timCo.setOrdersAsSupplier(l2);
        janInc.setOrdersAsSupplier(l1);

        order1.setSupplier(janInc);
        order2.setSupplier(timCo);
        order3.setSupplier(janInc);
        order4.setSupplier(timCo);

        order1.setCustomer(timCo);
        order2.setCustomer(janInc);
        order3.setCustomer(timCo);
        order4.setCustomer(janInc);

        /*
         *
         * Adding objects to database
         *
         */

        // Supplier
        userManager.persist(timCo);
        userManager.persist(janInc);

        // TrackingService
        userManager.persist(bpost1);
        userManager.persist(postnl1);
        userManager.persist(bpost2);
        userManager.persist(postnl2);

        // TrackingService ContactPerson
        userManager.persist(bpostPerson1);
        userManager.persist(bpostPerson2);
        userManager.persist(bpostPerson3);
        userManager.persist(bpostPerson4);
        userManager.persist(postnlPerson1);
        userManager.persist(postnlPerson2);
        userManager.persist(bpostDetails);
        userManager.persist(postnlDetails);

        // Products
        userManager.persist(product1);
        userManager.persist(product2);
        userManager.persist(product3);
        userManager.persist(product4);
        userManager.persist(product5);

        // Order
        userManager.persist(order1);
        userManager.persist(order2);
        userManager.persist(order3);
        userManager.persist(order4);

        // Order Notification
        userManager.persist(postedNotification);
        userManager.persist(processedNotification);
        userManager.persist(deliveredNotification);

        // Packaging
        userManager.persist(timCoSmallStandard);
        userManager.persist(timCoBigCustom);
        userManager.persist(janIncMediumCustom);
        userManager.persist(janIncMediumCustomOld);

        // Users
        userManager.persist(admin);
        userManager.persist(warehouseman);

        userManager.getTransaction().commit();
        userManager.close();
    }
}