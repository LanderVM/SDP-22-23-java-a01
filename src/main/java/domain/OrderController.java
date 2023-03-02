package domain;

import java.util.Arrays;
import java.util.List;

import exceptions.OrderStatusException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.OrderJPADao;
import util.JPAUtil;

public class OrderController {

    private final ObservableList<String> transportServicesObservableList;

    private final OrderJPADao orderJPADao;

    public OrderController(OrderJPADao orderJPADao) {
        this.orderJPADao = orderJPADao;
        transportServicesObservableList = FXCollections.observableArrayList(this.giveTransportServicesAsString());
    }

    public List<Order> getOrderList() {
        return orderJPADao.getAll();
    }


    public List<Order> getPostedOrdersList() {
        return orderJPADao.getAllPosted();
    }

    private List<String> giveTransportServicesAsString() {
        return Arrays.stream(TransportService.values()).map(Enum::name).toList();
    }

    public ObservableList<String> getTransportServicesObservableList() {
        return this.transportServicesObservableList;
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
        order.setTrackingCode((int) (Math.random() * 10));
        order.setStatus(Status.PROCESSED);

        orderJPADao.update(order);
    }

}