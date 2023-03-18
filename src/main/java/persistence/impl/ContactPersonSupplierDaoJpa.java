package persistence.impl;

import domain.ContactPersonSupplier;
import jakarta.persistence.EntityManager;
import persistence.ContactPersonSupplierDao;

import java.util.List;

public class ContactPersonSupplierDaoJpa extends GenericDaoJpa<ContactPersonSupplier> implements ContactPersonSupplierDao {

    public ContactPersonSupplierDaoJpa(EntityManager entityManager) {
        super(ContactPersonSupplier.class, entityManager);
    }

    @Override
    public List<ContactPersonSupplier> getAllForSupplier(String email) {
        return entityManager.createNamedQuery("ContactPersonSupplier.findAllForSupplier", ContactPersonSupplier.class).setParameter(1, email).getResultList();
    }
}
