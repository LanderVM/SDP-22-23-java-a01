package application;

import domain.DomainController;
import domain.Product;
import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class BackEndDev {

    public static void main(String[] args) {
        seedDatabase();
        DomainController domainController = new DomainController();
        domainController.generateListOrders().forEach(System.out::println);
        JPAUtil.getOrdersEntityManagerFactory().close();
    }

    private static void seedDatabase() {
        Product p1 = new Product("test_product een", 1.0);
        Product p2 = new Product("test_product twee", 2.0);
        Product p3 = new Product("test_product drie", 4.5);
        Product p4 = new Product("test_product vier", 8.9);
        Product p5 = new Product("test_product vijf", 11.30);

        EntityManager entityManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        entityManager.persist(p4);
        entityManager.persist(p5);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}