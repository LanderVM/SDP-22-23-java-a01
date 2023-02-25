package domain;

import java.util.List;

import util.JPAUtil;

import javax.persistence.EntityManager;

public class DomainController {

    private List<Product> orders;

    public DomainController() {
        orders = getOrders();
    }

    public List<Product> generateListOrders() {
        return orders;
    }


    private List<Product> getOrders() {
        EntityManager entityManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        List<Product> ordersList = entityManager.createNamedQuery("Products.findAll", Product.class).getResultList();
        entityManager.getTransaction().commit();

        entityManager.close();
        JPAUtil.getOrdersEntityManagerFactory().close();
        return ordersList;
    }
}
