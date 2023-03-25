package persistence.impl;

import domain.Packaging;
import jakarta.persistence.EntityManager;
import persistence.PackagingDao;

import java.util.Collections;
import java.util.List;

public class PackagingDaoJpa extends GenericDaoJpa<Packaging> implements PackagingDao {

    public PackagingDaoJpa(EntityManager entityManager, int supplierId) {
        super(Packaging.class, entityManager, supplierId);
    }

    public void delete(int packagingId) {
        entityManager.getTransaction().begin();
        Packaging packaging = get(packagingId);
        entityManager.remove(packaging);
        entityManager.getTransaction().commit();
    }

    public List<Packaging> getAll() {
        List<Packaging> result = entityManager.createNamedQuery("Packaging.findAll", Packaging.class).setParameter(1, supplierId).getResultList();
        return Collections.unmodifiableList(result);
    }

    public void add(Packaging packaging) {
        entityManager.getTransaction().begin();
        entityManager.persist(packaging);
        entityManager.getTransaction().commit();
    }

    public boolean exists(String name) {
        return !entityManager.createNamedQuery("Packaging.findNameExists").setParameter(1, name).setParameter(2, supplierId).getResultList().isEmpty();
    }

    public Packaging get(String name) {
        return entityManager.createNamedQuery("Packaging.findByName", Packaging.class).setParameter(1, name).setParameter(2, supplierId).getSingleResult();
    }
}
