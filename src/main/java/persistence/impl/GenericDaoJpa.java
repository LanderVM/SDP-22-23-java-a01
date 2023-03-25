package persistence.impl;

import jakarta.persistence.EntityManager;
import persistence.GenericDao;

public class GenericDaoJpa<T> implements GenericDao<T> {

    final EntityManager entityManager;
    private final Class<T> type;
    private final int supplierId;

    public GenericDaoJpa(Class<T> type, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.type = type;
        this.supplierId = 1; // TODO
    }

    public GenericDaoJpa(Class<T> type, EntityManager entityManager, int supplierId) {
        this.entityManager = entityManager;
        this.type = type;
        this.supplierId = supplierId;
    }

    public T get(int id) {
        return entityManager.createNamedQuery(type.getSimpleName() + ".findById", type).setParameter(1, id).getSingleResult();
    }

    public void update(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    public void insert(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

}
