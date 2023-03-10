package domain;

import java.util.List;

import exceptions.OrderStatusException;
import gui.view.OrderView;
import jakarta.persistence.EntityNotFoundException;
import persistence.OrderJPADao;
import persistence.UserJPADao;

public class OrderController {


    private final OrderJPADao orderJPADao;
    private final UserJPADao userJPADao;

    public OrderController(OrderJPADao orderJPADao,UserJPADao userJPADao) {
        this.orderJPADao = orderJPADao;
        this.userJPADao = userJPADao;
    }

    public List<OrderView> getOrderList() {
        return orderJPADao.getAll().stream().map(OrderView::new).toList();
    }

    public List<OrderView> getPostedOrdersList() {
        return orderJPADao.getAllPosted().stream().map(OrderView::new).toList();
    }
    
    public List<OrderView> getOrderListForSupplier(int userId) {
    	User user = userJPADao.get(userId);
        return orderJPADao.getAllForSupplier(user.getSupplier().getSupplierId()).stream().map(OrderView::new).toList();
    }

    public List<OrderView> getPostedOrdersListForSupplier(int userId) {
    	User user = userJPADao.get(userId);
        return orderJPADao.getAllPostedForSupplier(user.getSupplier().getSupplierId()).stream().map(OrderView::new).toList();
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