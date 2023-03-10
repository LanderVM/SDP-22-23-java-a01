package persistence;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import domain.Supplier;
import gui.view.CustomerView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class SupplierJPADao implements JPADao<Supplier, Integer>{

	private EntityManager entityManager;
	
	public SupplierJPADao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Supplier get(Integer id) throws NoResultException {
		TypedQuery<Supplier> query = entityManager.createNamedQuery("Supplier.findById", Supplier.class);
	    return query.setParameter(1, id).getSingleResult();
	}

	public Supplier get(String mail) throws NoResultException {
		TypedQuery<Supplier> query = entityManager.createNamedQuery("Supplier.findByEmail", Supplier.class);
	    return query.setParameter(1, mail).getSingleResult();
	}

	@Override
	public List<Supplier> getAll() {		
		 return Collections.unmodifiableList(entityManager.createNamedQuery("Supplier.findAll",Supplier.class).getResultList());	
	}
	
	public List<Supplier> getAllCustomersOrderedByName() {
		return Collections.unmodifiableList(entityManager.createNamedQuery("Supplier.findAllCustomersOrderedByName", Supplier.class).getResultList());
	}
	
	public List<Supplier> getCustomers() {
		return Collections.unmodifiableList(entityManager.createNamedQuery("Supplier.findAllWithOrdersAsCustomer", Supplier.class).getResultList());
	}
	
	public List<Supplier> getAllCustomersForSupplier(int supplierId) {
		return Collections.unmodifiableList(entityManager.createNamedQuery("Supplier.findAllCustomersForSupplier", Supplier.class).setParameter(1, supplierId).getResultList());
	}

	@Override
	public void update(Supplier supplier) {
		entityManager.getTransaction().begin();
        entityManager.merge(supplier);
        entityManager.getTransaction().commit();
	}


}
