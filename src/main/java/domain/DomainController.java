package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DomainController {

    private final ObservableList<Order> ordersList;
    private final ObservableList<String> transportServicesList;

    public DomainController() {
        ordersList = FXCollections.observableArrayList(); //TODO opvullen observable lijst
        transportServicesList = getTransportServicesList();
    }


//    private List<Product> getProducts() {
//        EntityManager entityManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
//        entityManager.getTransaction().begin();
//        List<Product> ordersList = entityManager.createNamedQuery("Product.findAll", Product.class).getResultList();
//        entityManager.getTransaction().commit();
//
//        entityManager.close();
//        return ordersList;
//    }

    public ObservableList<Order> getOrdersList() {
        return FXCollections.unmodifiableObservableList(this.ordersList);
    }

    public ObservableList<String> getTransportServicesList() {
        return this.transportServicesList;
    }

    public String getOrderOverview(int orderId) {
        //TODO
        return "";
    }

    public void processOrder(int orderId, String transportService) {
        //TODO
    }
}
