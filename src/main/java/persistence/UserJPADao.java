package persistence;

import java.util.Collections;
import java.util.List;

import domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UserJPADao implements JPADao<User> {

    EntityManager entityManager;

    public UserJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<?> getAll() {
        return Collections.unmodifiableList(entityManager.createNamedQuery("User.findAll", User.class).getResultList());
    }

    public User get(String email) throws NoResultException {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findByEmail", User.class);
        return query.setParameter(1, email).getSingleResult();
    }


    @Override
    public User get(int id) throws NoResultException {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findById", User.class);
        return query.setParameter(1, id).getSingleResult();
    }

    @Override
    public void update(User user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

}
