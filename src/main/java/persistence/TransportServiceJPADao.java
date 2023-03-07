package persistence;

import domain.NewTransportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class TransportServiceJPADao implements JPADao<NewTransportService> {

    EntityManager entityManager;

    public TransportServiceJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public NewTransportService get(int id) {
        TypedQuery<NewTransportService> query = entityManager.createNamedQuery("TransportService.findById", NewTransportService.class);
        return query.setParameter(1, id).getSingleResult();
    }

    @Override
    public List<NewTransportService> getAll() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("TransportService.findAll", NewTransportService.class).getResultList());
    }

    public List<NewTransportService> getAllActive() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("TransportService.findAllActive", NewTransportService.class).getResultList());
    }

    @Override
    public void update(NewTransportService transportService) {
        entityManager.getTransaction().begin();
        entityManager.merge(transportService);
        entityManager.getTransaction().commit();
    }
}
