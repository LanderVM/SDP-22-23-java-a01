package persistence.impl;

import domain.Packaging;
import jakarta.persistence.EntityManager;
import persistence.PackagingDao;

public class PackagingDaoJpa extends GenericDaoJpa<Packaging> implements PackagingDao   {

    public PackagingDaoJpa(EntityManager entityManager) {
        super(Packaging.class, entityManager);
    }

    public void delete(int packagingId) {
        entityManager.getTransaction().begin();
        Packaging packaging = get(packagingId);
        entityManager.remove(packaging);
        entityManager.getTransaction().commit();
    }
}
