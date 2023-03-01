package persistence;

import domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderJPADao implements JPADao<Order> {

    EntityManager entityManager;

    public OrderJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Order> get(int id) {
        TypedQuery<Order> query = entityManager.createNamedQuery("Order.findById", Order.class);
        Order order = query.setParameter(1, id).getSingleResult();
        return order == null ?
                Optional.empty() :
                Optional.of(order);
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
        entityManager.merge(order);
    }
}
