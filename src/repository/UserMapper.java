package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import domain.User;
import util.JPAUserUtil;

public class UserMapper {
	
	public User getUser (String accountName) {
		EntityManagerFactory emf = JPAUserUtil.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		User user;
		
		em.getTransaction().begin();
		user = em.find(User.class, accountName);
		em.getTransaction().commit();
		em.close();
		
		
		return user;
	}
	public void makeUser (String accountName, String password,boolean isAdmin) {
		//TODO
	}
}
