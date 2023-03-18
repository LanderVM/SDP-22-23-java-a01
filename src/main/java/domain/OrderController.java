package domain;

import java.util.List;
import java.util.function.Function;

import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.ProductView;
import jakarta.persistence.EntityNotFoundException;
import persistence.OrderDao;
import persistence.UserJPADao;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class OrderController {


    private final OrderDao orderDao;
    private final UserJPADao userJPADao;

    public OrderController(OrderDao orderDao, UserJPADao userJPADao) {
        this.orderDao = orderDao;
        this.userJPADao = userJPADao;
    }

    public List<OrderView> getOrderList() {
        return orderDao.getAll().stream().map(OrderView::new).toList();
    }

    public OrderView getOrderByIdView(int id) {
        return new OrderView(orderDao.get(id));
    }

    public Order getOrderById(int id) {
        return orderDao.get(id);
    }

    public List<ProductView> getProductsList(int id) {
        Order order = orderDao.get(id);
        return order.getProductsList()
                .stream()
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .map(entry -> new ProductView(entry.getKey(), entry.getValue().intValue()))
                .toList();
    }

    public void processOrder(int orderId, TransportService transportService) throws EntityNotFoundException, OrderStatusException {
        Order order = orderDao.get(orderId);
        if (!order.getStatus().equals(Status.POSTED))
            throw new OrderStatusException("Order must have status POSTED in order to get processed!");

        order.setTransportService(transportService);
        order.setStatus(Status.PROCESSED);
        order.addNotification(new Notification(order));
        order.generateTrackingCode();

        orderDao.update(order);
    }
}