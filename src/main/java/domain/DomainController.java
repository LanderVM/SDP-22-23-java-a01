package domain;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.OrderJPADao;
import util.JPAUtil;

public class DomainController {

	private ObservableList<Order> observableOrdersList;
    private ObservableList<String> transportServicesObservableList;
    
    private List<Order> orderList;
    

    public DomainController() {
    	
    	EntityManager entityManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
        OrderJPADao orderJPADao = new OrderJPADao(entityManager);
        orderList = orderJPADao.getAll();
    	observableOrdersList = FXCollections.observableArrayList(orderList);//TODO opvullen observable lijst met arraylist van orders die unprocessed zijn
        transportServicesObservableList = FXCollections.observableArrayList(this.giveTransportServicesAsString());
    }
    
    

    public List<Order> getOrderList() {
    	//return FXCollections.unmodifiableObservableList(this.observableOrdersList);
    	//return this.filteredOrdersList;
    	return this.orderList;
    }
    private List<String> giveTransportServicesAsString () {
    	TransportService[] array =  TransportService.values();
    	String [] array2 = new String[array.length];
    	for (int i = 0;i<array.length;i++) {
    		array2[i]=array[i].toString();
    	}
    	return Arrays.asList(array2);
    }
    public ObservableList<String> getTransportServicesObservableList () {
    	return this.transportServicesObservableList;
    }

//	public void changeFilter(String filterValue) {
//		this.filteredOrdersList.setPredicate(order -> {
//			if (filterValue== null || filterValue.isEmpty()) {
//				return true;
//			}
//			String id = Integer.toString(order.getOrderId());
//			return id.contains(filterValue);
//
//		});
//		 TODO
//	}

    public String getOrderOverview(int orderId) {
        //TODO
        return "";
    }

    public void processOrder(int orderId, String transportService) {
    	EntityManager entityManager = JPAUtil.getOrdersEntityManagerFactory().createEntityManager();
        OrderJPADao orderJPADao = new OrderJPADao(entityManager);
        Order order = orderJPADao.get(orderId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Order with current orderId could not be found");
                });

        // TODO
        //order.setTransportService(transportService);
        orderJPADao.update(order);
    }
}