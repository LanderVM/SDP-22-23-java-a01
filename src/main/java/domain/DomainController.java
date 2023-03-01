package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import persistence.OrderJPADao;
import util.JPAUtil;

public class DomainController {

    private ObservableList<Order> observableOrdersList;
    private FilteredList<Order> filteredOrdersList;
    private SortedList<Order> sortedOrdersList;
    private Comparator<Order> sortOrdersOnDate;
    private ObservableList<String> transportServicesObservableList;

    private OrderJPADao orderJPADao;

    public DomainController(OrderJPADao orderJPADao) {
        this.orderJPADao = orderJPADao;

        observableOrdersList = FXCollections.observableArrayList();//TODO opvullen observable lijst met arraylist van orders die unprocessed zijn
        filteredOrdersList = new FilteredList<Order>(observableOrdersList, p -> true);
        sortOrdersOnDate = (o1, o2) -> {
            return o1.getDate().compareTo(o2.getDate());
        };
        sortedOrdersList = new SortedList<Order>(filteredOrdersList);
        transportServicesObservableList = FXCollections.observableArrayList(this.giveTransportServicesAsString());
    }

    public ObservableList<Order> getObservableOrdersList() {
        //return FXCollections.unmodifiableObservableList(this.observableOrdersList);
        //return this.filteredOrdersList;
        return this.sortedOrdersList;
    }

    private List<String> giveTransportServicesAsString() {
        TransportService[] array = TransportService.values();
        String[] array2 = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            array2[i] = array[i].toString();
        }
        return Arrays.asList(array2);
    }

    public ObservableList<String> getTransportServicesObservableList() {
        return this.transportServicesObservableList;
    }

    public void changeFilter(String filterValue) {
        this.filteredOrdersList.setPredicate(order -> {
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }
            String id = Integer.toString(order.getOrderId());
            return id.contains(filterValue);

        });

    }

    public String getOrderOverview(int orderId) {
        //TODO
        return "";
    }

    public void processOrder(int orderId, TransportService transportService) throws EntityNotFoundException {
        Order order = orderJPADao.get(orderId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Order with current orderId could not be found");
                });
        order.setTransportService(transportService);
        order.setStatus(Status.PROCESSED);
        orderJPADao.update(order);
    }

}