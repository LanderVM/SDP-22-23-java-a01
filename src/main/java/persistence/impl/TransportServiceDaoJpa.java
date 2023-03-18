package persistence.impl;

import domain.TransportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import persistence.TransportServiceDao;

public class TransportServiceDaoJpa extends GenericDaoJpa<TransportService> implements TransportServiceDao {

    public TransportServiceDaoJpa(EntityManager entityManager) {
        super(TransportService.class, entityManager);
    }

    @Override
    public void insert(TransportService transportService) {
        entityManager.getTransaction().begin();
        entityManager.persist(transportService.getTrackingCodeDetails());
        entityManager.merge(transportService);
        entityManager.getTransaction().commit();
    }

    public boolean exists(String name) {
        TypedQuery<TransportService> query = entityManager.createNamedQuery("TransportService.findNameExists", TransportService.class);
        return !query.setParameter(1, name).getResultList().isEmpty();
    }

    public TransportService get(String name) throws NoResultException {
        TypedQuery<TransportService> query = entityManager.createNamedQuery("TransportService.findByName", TransportService.class);
        return query.setParameter(1, name).getSingleResult();
    }
}