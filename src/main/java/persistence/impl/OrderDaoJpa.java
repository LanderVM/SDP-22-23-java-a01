package persistence.impl;

import domain.Order;
import domain.OrderLine;
import domain.Supplier;
import gui.view.OrderTrackingMailDTO;
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

    public List<Order> getAllForUser(int id) {
        return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAllForUser", Order.class).setParameter(1, id).getResultList());
    }

    public List<OrderLine> getOrderLinesForOrder(int orderId) {
        return entityManager.createNamedQuery("Order.getOrderLinesForOrder", OrderLine.class).setParameter(1, orderId).getResultList();
    }

    public Supplier getCustomerForOrder(int orderId) {
        return entityManager.createNamedQuery("Order.getCustomerForOrder", Supplier.class).setParameter(1, orderId).getSingleResult();
    }

    public List<Order> getAllForUserPosted(int userId) {
        return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAllForUserPosted", Order.class).setParameter(1, userId).getResultList());
    }

    public List<Order> getAllForUserOpen(int userId) {
        return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAllForUserOpen", Order.class).setParameter(1, userId).getResultList());
    }

    @Override
    public OrderTrackingMailDTO getUserDataForProcessedMail(int orderId) {
        return entityManager.createNamedQuery("Order.getUserDataForProcessedMail", OrderTrackingMailDTO.class).setParameter(1, orderId).getSingleResult();
    }
}
