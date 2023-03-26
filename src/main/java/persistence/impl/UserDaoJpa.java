package persistence.impl;

import java.util.Collections;
import java.util.List;

import domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import persistence.UserDao;

public class UserDaoJpa extends GenericDaoJpa<User> implements UserDao {

    public UserDaoJpa(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public User get(String email) {
        return entityManager.createNamedQuery("User.findByEmail", User.class).setParameter(1, email).getSingleResult();
    }
    
    @Override
    public List<User> getAllForSupplier(int id) {
    	List<User> result = entityManager.createNamedQuery("User.findAllEmployeesForSupplier", User.class).setParameter(1, id).getResultList();
        return Collections.unmodifiableList(result);
    }
    
    @Override
    public void insert(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

	@Override
	public boolean exists(String email) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByEmail", User.class);
        return !query.setParameter(1, email).getResultList().isEmpty();	
    }
}
