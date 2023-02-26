package domain;

import java.util.List;

import exceptions.UserDoesntExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.UserMapper;
import util.JPAUtil;

import javax.persistence.EntityManager;

public class DomainController {

    private List<Product> orders;
    private User user;
    private UserRepo userRepo;
    private ObservableList<Order> observableOrdersList;

    public DomainController() {
        userRepo = new UserRepo(new UserMapper());
        orders = getOrders();
        observableOrdersList = FXCollections.observableArrayList();//TODO opvullen observable lijst
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
    
    public ObservableList<Order> getObservableOrdersList () {
    	return FXCollections.unmodifiableObservableList(this.observableOrdersList);
    }
    public String[] giveTransportServices () {
    	TransportService[] array =  TransportService.values();
    	String [] array2 = new String[array.length];
    	for (int i = 0;i<array.length;i++) {
    		array2[i]=array[i].toString();
    	}
    	return array2;
    }
    public String giveOverviewOrder (int id) {
    	//TODO
    	return "";
    }
    public void processOrder (String transportService,int id) {
    	//TODO
    }
}
