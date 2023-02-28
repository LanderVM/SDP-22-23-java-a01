package repository;

import jakarta.persistence.EntityManager;

import domain.User;
import jakarta.persistence.EntityManagerFactory;
import util.JPAUserUtil;

public class UserMapper {

    public User getUser(String accountName) {

        EntityManagerFactory emf = JPAUserUtil.getEntityManagerFactory();
        EntityManager em = JPAUserUtil.getEntityManagerFactory().createEntityManager();

        User user;

        em.getTransaction().begin();
        user = em.find(User.class, accountName);
        em.getTransaction().commit();
        em.close();

        return user;
    }

}
