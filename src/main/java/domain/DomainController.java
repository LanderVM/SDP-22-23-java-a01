package domain;

import java.util.List;

import exceptions.UserDoesntExistException;
import repository.UserMapper;
import util.JPAUtil;

import javax.persistence.EntityManager;

public class DomainController {

    private List<Product> orders;
    private User user;
    private UserRepo userRepo;

    public DomainController() {
        userRepo = new UserRepo(new UserMapper());
        orders = getOrders();
    }

    public List<Product> generateListOrders() {
        return orders;
    }


    private List<Product> getOrders() {
        EntityManager entityManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        List<Product> ordersList = entityManager.createNamedQuery("Products.findAll", Product.class).getResultList();
        entityManager.getTransaction().commit();

        entityManager.close();
        JPAUtil.getOrdersEntityManagerFactory().close();
        return ordersList;
    }

    public boolean checkUser(String accountName, String password) throws UserDoesntExistException {
    	
        user = userRepo.requestUser(accountName);
        
        if (user == null) {
            throw new UserDoesntExistException();
        }
        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean userIsAdmin() {
        return user.isAdmin() == 1;
    }
}
