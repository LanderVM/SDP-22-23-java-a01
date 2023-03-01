package persistence;

import domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Order> {

    EntityManager entityManager;

    public OrderDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Order> get(int id) {
        Query query = entityManager.createNamedQuery("Order.findById");
        return Optional.of((Order) query.setParameter(1, id).getSingleResult());
    }

    @Override
    public List getAll() {
        return null;
    }
}
