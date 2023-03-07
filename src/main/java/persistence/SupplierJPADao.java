package persistence;

import java.util.Collections;
import java.util.List;

import domain.Order;
import domain.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class SupplierJPADao implements JPADao<Supplier,String>{

	private EntityManager entityManager;
	
	public SupplierJPADao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Supplier get(String id) throws NoResultException {
		TypedQuery<Supplier> query = entityManager.createNamedQuery("Supplier.findById", Supplier.class);
	    return query.setParameter(1, id).getSingleResult();
	}

	public Supplier getByMail(String mail) throws NoResultException {
		TypedQuery<Supplier> query = entityManager.createNamedQuery("Supplier.findByEmail", Supplier.class);
	    return query.setParameter(1, mail).getSingleResult();
	}
	
	@Override
	public List<Supplier> getAll() {
		
		 return Collections.unmodifiableList(entityManager.createNamedQuery("Supplier.findAll",Supplier.class).getResultList());
		
	}
	
	public List<Supplier> getAllWithOrdersAsCustomer() {
		return Collections.unmodifiableList(entityManager.createNamedQuery("Supplier.findAllWithOrdersAsCustomer", Supplier.class).getResultList());
	}

	@Override
	public void update(Supplier supplier) {
		entityManager.getTransaction().begin();
        entityManager.merge(supplier);
        entityManager.getTransaction().commit();
	}

}
