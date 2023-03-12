package persistence;

import domain.TransportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class TransportServiceJPADao implements JPADao<TransportService,Integer> {

    EntityManager entityManager;

    public TransportServiceJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public TransportService get(Integer id) {
        TypedQuery<TransportService> query = entityManager.createNamedQuery("TransportService.findById", TransportService.class);
        return query.setParameter(1, id).getSingleResult();
    }

    @Override
    public List<TransportService> getAll() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("TransportService.findAll", TransportService.class).getResultList());
    }

    public List<TransportService> getAllActive() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("TransportService.findAllActive", TransportService.class).getResultList());
    }

    public void add(TransportService transportService) {
        entityManager.getTransaction().begin();
        entityManager.persist(transportService.getTrackingCodeDetails());
        entityManager.persist(transportService);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(TransportService transportService) {
        entityManager.getTransaction().begin();
        entityManager.merge(transportService);
        entityManager.getTransaction().commit();
    }
}
