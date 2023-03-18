package persistence.impl;

import jakarta.persistence.EntityManager;
import persistence.GenericDao;

import java.util.Collections;
import java.util.List;

public class GenericDaoJpa<T> implements GenericDao<T> {

    final EntityManager entityManager;
    private final Class<T> type;

    public GenericDaoJpa(Class<T> type, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.type = type;
    }

    @Override
    public T get(int id) {
        return entityManager.createNamedQuery(type.getSimpleName() + ".findById", type).setParameter(1, id).getSingleResult();
    }

    @Override
    public List<T> getAll() {
        List<T> result = entityManager.createNamedQuery(type.getSimpleName() + ".findAll", type).getResultList();
        return Collections.unmodifiableList(result);
    }

    @Override
    public void update(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void insert(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

}
