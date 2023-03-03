package persistence;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import domain.Order;
import domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserJPADoa implements JPADao<User> {

	EntityManager entityManager;
	
	public UserJPADoa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	@Override
	public List<?> getAll() {
		return Collections.unmodifiableList(entityManager.createNamedQuery("User.findAll", User.class).getResultList());
	}

	public Optional<User> getUser(String email) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByEmail", User.class);
		User user = query.setParameter("email", email).getSingleResult();
        return user == null ?
                Optional.empty() :
                Optional.of(user);
	}
	
	
	@Override
	public Optional<User> get(int id) {
		return null;
	}
	
	@Override
	public void update(User order) {
	}

}
