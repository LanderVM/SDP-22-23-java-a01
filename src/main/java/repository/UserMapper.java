package repository;

import jakarta.persistence.EntityManager;

import domain.User;
import jakarta.persistence.EntityManagerFactory;
import util.JPAUtil;

public class UserMapper { // TODO dao

    public User getUser(String accountName) {

        EntityManagerFactory emf = JPAUtil.getUserEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user;

        em.getTransaction().begin();
        user = em.find(User.class, accountName);
        em.getTransaction().commit();
        em.close();

        return user;
    }

}
