package persistence;

import domain.Supplier;
import domain.TransportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    
    public TransportService get(String name) throws NoResultException {
		TypedQuery<TransportService> query = entityManager.createNamedQuery("TransportService.findByName", TransportService.class);
	    return query.setParameter(1, name).getSingleResult();
	}

    @Override
    public List<TransportService> getAll() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("TransportService.findAll", TransportService.class).getResultList());
    }

    public boolean exists(String name) {
        TypedQuery<TransportService> query = entityManager.createNamedQuery("TransportService.findNameExists", TransportService.class);
        return !query.setParameter(1, name).getResultList().isEmpty();
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
