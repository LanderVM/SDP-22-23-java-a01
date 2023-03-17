package domain;

import java.util.List;
import java.util.function.Function;

import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.ProductView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.OrderJPADao;
import persistence.TransportServiceJPADao;
import persistence.UserJPADao;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class OrderController {


    private final OrderJPADao orderJPADao;
    private final UserJPADao userJPADao;
    private final TransportServiceJPADao transportServiceJPADao;

    public OrderController(OrderJPADao orderJPADao,UserJPADao userJPADao,TransportServiceJPADao transportServiceJPADao) {
        this.orderJPADao = orderJPADao;
        this.userJPADao = userJPADao;
        this.transportServiceJPADao = transportServiceJPADao;
    }

    public ObservableList<OrderView> getOrderList() {
        return FXCollections.observableArrayList(orderJPADao.getAll().stream().map(OrderView::new).toList());
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

    public OrderView getOrderByIdView(int id) {
        return new OrderView(orderJPADao.get(id));
    }

    public String getOrderOverview(int orderId) {
        return orderJPADao.get(orderId).toString();
    }

    public Order getOrderById(int id) {
        return orderJPADao.get(id);
    }

    public List<ProductView> getProductsList(int id) {
        Order order = orderJPADao.get(id);
        return order.getProductsList()
                .stream()
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .map(entry -> new ProductView(entry.getKey(), entry.getValue().intValue()))
                .toList();
    }

    public void processOrder(int orderId, String transportServiceName) throws EntityNotFoundException, OrderStatusException {
        Order order = orderJPADao.get(orderId);
        TransportService transportService =  transportServiceJPADao.get(transportServiceName);
        if (!order.getStatus().equals(Status.POSTED))
            throw new OrderStatusException("Order must have status POSTED in order to get processed!");

        order.setTransportService(transportService);
        order.setStatus(Status.PROCESSED);
        order.addNotification(new Notification(order));
        order.generateTrackingCode();

        orderJPADao.update(order);
    }
}