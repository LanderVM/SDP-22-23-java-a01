package persistence.impl;

import java.util.List;

import domain.Order;
import domain.Supplier;
import jakarta.persistence.EntityManager;
import persistence.SupplierDao;

public class SupplierDaoJpa extends GenericDaoJpa<Supplier> implements SupplierDao {

    public SupplierDaoJpa(EntityManager entityManager) {
        super(Supplier.class, entityManager);
    }

    public Supplier get(String email) {
        return entityManager.createNamedQuery("Supplier.findByEmail", Supplier.class).setParameter(1, email).getSingleResult();
    }
    
    
    public List<Supplier> getCustomersForSupplier (int supplierId) {
    	return entityManager.createNamedQuery("Supplier.findAllCustomersForSupplier",Supplier.class).setParameter(1, supplierId).getResultList();
    }
    
    public List<Order> getOrdersForCustomer (String customerMail) {
    	return entityManager.createNamedQuery("Supplier.getAllOrdersForCustomer",Order.class).setParameter(1, customerMail).getResultList();
    }
}
