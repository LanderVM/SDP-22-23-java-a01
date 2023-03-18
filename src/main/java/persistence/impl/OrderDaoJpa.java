package persistence.impl;

import domain.Order;
import jakarta.persistence.EntityManager;
import persistence.OrderDao;

import java.util.Collections;
import java.util.List;


public class OrderDaoJpa extends GenericDaoJpa<Order> implements OrderDao {

    public OrderDaoJpa(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> getAllForSupplier(int supplierId) {
        List<Order> result = entityManager.createNamedQuery("Order.findAllForSupplier", Order.class).setParameter(1, supplierId).getResultList();
        return Collections.unmodifiableList(result);
    }
}
