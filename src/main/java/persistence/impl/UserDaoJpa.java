package persistence.impl;

import domain.User;
import jakarta.persistence.EntityManager;
import persistence.UserDao;

public class UserDaoJpa extends GenericDaoJpa<User> implements UserDao {

    public UserDaoJpa(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public User get(String email) {
        return entityManager.createNamedQuery("User.findByEmail", User.class).setParameter(1, email).getSingleResult();
    }
}
