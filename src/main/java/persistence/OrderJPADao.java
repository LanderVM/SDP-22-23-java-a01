package persistence;

import domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class OrderJPADao implements JPADao<Order> {

    EntityManager entityManager;

    public OrderJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Order get(int id) throws NoResultException {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findById", Order.class);
        return query.setParameter(1, id).getSingleResult();
    }

    @Override
    public List<Order> getAll() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAll", Order.class).getResultList());
    }

    public List<Order> getAllPosted() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAllPosted", Order.class).getResultList());
    }

    @Override
    public void update(Order order) {
        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();
    }
}
