package persistence;

import domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class OrderJPADao implements JPADao<Order,Integer> {

    EntityManager entityManager;

    public OrderJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Order get(Integer id) throws NoResultException {
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
    
    public List<Order> getAllForSupplier(int id) {
        return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAllForSupplier", Order.class).setParameter(1, id).getResultList());
    }
    
    public List<Order> getAllForUser(int id) {
    	return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAllForUser", Order.class).setParameter(1, id).getResultList());
    }

    public List<Order> getAllPostedForSupplier(int id) {
        return Collections.unmodifiableList(entityManager.createNamedQuery("Order.findAllPostedForSupplier", Order.class).setParameter(1, id).getResultList());
    }

    @Override
    public void update(Order order) {
        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();
    }
}
