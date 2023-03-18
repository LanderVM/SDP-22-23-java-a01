package persistence;

import domain.Supplier;
import jakarta.persistence.EntityManager;

public class SupplierDaoJpa extends GenericDaoJpa<Supplier> implements SupplierDao {

    public SupplierDaoJpa(EntityManager entityManager) {
        super(Supplier.class, entityManager);
    }

    @Override
    public Supplier get(String email) {
        return entityManager.createNamedQuery("Supplier.findByEmail", Supplier.class).setParameter(1, email).getSingleResult();
    }
}
