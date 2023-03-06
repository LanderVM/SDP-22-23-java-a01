package domain;

import java.util.Arrays;
import java.util.List;

import exceptions.OrderStatusException;
import jakarta.persistence.EntityNotFoundException;
import persistence.OrderJPADao;

public class OrderController {


    private final OrderJPADao orderJPADao;

    public OrderController(OrderJPADao orderJPADao) {
        this.orderJPADao = orderJPADao;
    }

    public List<Order> getOrderList() {
        return orderJPADao.getAll();
    }

    public List<Order> getPostedOrdersList() {
        return orderJPADao.getAllPosted();
    }

    public List<String> getTransportServicesList() {
        return Arrays.stream(TransportService.values()).map(Enum::name).toList();
    }

    public String getOrderOverview(int orderId) {
        Order order = orderJPADao.get(orderId);
        return order.toString();
    }

    public void processOrder(int orderId, TransportService transportService) throws EntityNotFoundException, OrderStatusException {
        Order order = orderJPADao.get(orderId);
        if (!order.getStatus().equals(Status.POSTED))
            throw new OrderStatusException("Order must have status POSTED in order to get processed!");

        order.setTransportService(transportService);
        order.setTrackingCode((int) (Math.random() * 10));
        order.setStatus(Status.PROCESSED);

        orderJPADao.update(order);
    }

}