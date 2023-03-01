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

public class OrderController {

    private final ObservableList<Order> observableOrdersList;
    private final FilteredList<Order> filteredOrdersList;
    private final SortedList<Order> sortedOrdersList;
    private final Comparator<Order> sortOrdersOnDate;
    private final ObservableList<String> transportServicesObservableList;

    private final OrderJPADao orderJPADao;

    public OrderController(OrderJPADao orderJPADao) {
        this.orderJPADao = orderJPADao;

        observableOrdersList = FXCollections.observableArrayList(orderJPADao.getAllPosted());
        filteredOrdersList = new FilteredList<>(observableOrdersList, p -> true);
        sortOrdersOnDate = Comparator.comparing(Order::getDate);
        sortedOrdersList = new SortedList<>(filteredOrdersList);
        transportServicesObservableList = FXCollections.observableArrayList(this.giveTransportServicesAsString());
    }

    public ObservableList<Order> getObservableOrdersList() {
        return this.sortedOrdersList;
    }

    private List<String> giveTransportServicesAsString() {
        return Arrays.stream(TransportService.values()).map(Enum::name).toList();
    }

    public ObservableList<String> getTransportServicesObservableList() {
        return this.transportServicesObservableList;
    }

    public void changeFilter(String filterValue) {
        this.filteredOrdersList.setPredicate(order -> {
            if (filterValue == null || filterValue.isEmpty())
                return true;

            String id = Integer.toString(order.getOrderId());
            return id.contains(filterValue);
        });

    }

    public String getOrderOverview(int orderId) {
    	Order order = orderJPADao.get(orderId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Order with current orderId could not be found");
                });
    	return order.toString();
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