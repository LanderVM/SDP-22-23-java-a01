package persistence;

import java.util.List;

import domain.ContactPersonSupplier;
import jakarta.persistence.EntityManager;

public class ContactPersonSupplierJPADao implements JPADao<ContactPersonSupplier,String>{
	
	EntityManager entityManager;

    public ContactPersonSupplierJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@Override
	public ContactPersonSupplier get(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ContactPersonSupplier> getAllForSupplier (String supplierEmail) {
		return entityManager.createNamedQuery("ContactPersonSupplier.findAllForSupplier",ContactPersonSupplier.class).setParameter(1, supplierEmail).getResultList();
	}

	@Override
	public void update(ContactPersonSupplier contactPersonSupplier) {
		// TODO Auto-generated method stub
		
	}

	

}
