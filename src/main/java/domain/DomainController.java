package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import exceptions.OrderStatusException;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import persistence.OrderJPADao;

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
        filteredOrdersList = new FilteredList<>(observableOrdersList, p -> true);
        sortOrdersOnDate = Comparator.comparing(Order::getDate);
        sortedOrdersList = new SortedList<>(filteredOrdersList);
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

    public void processOrder(int orderId, TransportService transportService) throws EntityNotFoundException, OrderStatusException {
        Order order = orderJPADao.get(orderId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Order with current orderId could not be found");
                });
        if (!order.getStatus().equals(Status.POSTED))
            throw new OrderStatusException("Order must have status POSTED in order to get processed!");

        order.setTransportService(transportService);
        order.setTrackingCode();
        order.setStatus(Status.PROCESSED);

        orderJPADao.update(order);
    }

}