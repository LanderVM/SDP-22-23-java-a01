package persistence.impl;

import jakarta.persistence.EntityManager;
import persistence.GenericDao;

public class GenericDaoJpa<T> implements GenericDao<T> {

    final EntityManager entityManager;
    private final Class<T> type;

    public GenericDaoJpa(Class<T> type, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.type = type;
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

    public boolean exists(int id) {
        return !entityManager.createNamedQuery(type.getSimpleName() + ".findExists", type).setParameter(1, id).getResultList().isEmpty();
    }

}