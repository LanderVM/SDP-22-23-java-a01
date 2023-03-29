package persistence.impl;

import java.util.Collections;
import java.util.List;

import domain.Carrier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import persistence.CarrierDao;

public class CarrierDaoJpa extends GenericDaoJpa<Carrier> implements CarrierDao {

    public CarrierDaoJpa(EntityManager entityManager) {
        super(Carrier.class, entityManager);
    }

    @Override
    public void insert(Carrier carrier) {
        entityManager.getTransaction().begin();
        entityManager.persist(carrier.getTrackingCodeDetails());
        entityManager.merge(carrier);
        entityManager.getTransaction().commit();
    }

    public boolean exists(String name) {
        return !entityManager.createNamedQuery("Carrier.findNameExists").setParameter(1, name).getResultList().isEmpty();
    }
    
    public boolean existsForSupplier (String name, int supplierId) {
    	TypedQuery<Carrier> query = entityManager.createNamedQuery("Carrier.findNameExistsForSupplier", Carrier.class);
    	return !query.setParameter("supplierId", supplierId).setParameter("name", name).getResultList().isEmpty();
    }

    public Carrier get(String name) throws NoResultException {
        TypedQuery<Carrier> query = entityManager.createNamedQuery("Carrier.findByName", Carrier.class);
        return query.setParameter(1, name).getSingleResult();
    }
    
    public Carrier getForSupplier(String name, int supplierId) throws NoResultException {
        TypedQuery<Carrier> query = entityManager.createNamedQuery("Carrier.findByNameForSupplier", Carrier.class);
        return query.setParameter("supplierId", supplierId).setParameter("name", name).getSingleResult();
    }
    
    public List<String> getAllNamesForSupplier(int supplierId) {
    	return Collections.unmodifiableList(entityManager.createNamedQuery("Carrier.findAllNamesForSupplier",String.class).setParameter(1, supplierId).getResultList());
    }
    
    public List<Carrier> getAllForSupplier (int supplierId) {
    	return entityManager.createNamedQuery("Carrier.findAllForSupplier", Carrier.class).setParameter(1, supplierId).getResultList();
    }
    
}