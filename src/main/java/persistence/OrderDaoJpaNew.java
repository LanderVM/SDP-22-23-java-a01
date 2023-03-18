package persistence;

import domain.Order;
import jakarta.persistence.EntityManager;

import java.util.Collections;
import java.util.List;


public class OrderDaoJpaNew extends GenericDaoJpa<Order> implements OrderDao {

    public OrderDaoJpaNew(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> getAllForSupplier(int supplierId) {
        List<Order> result = entityManager.createNamedQuery("Order.findAllForSupplier", Order.class).getResultList();
        return Collections.unmodifiableList(result);
    }
}
