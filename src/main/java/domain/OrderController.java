package domain;

import java.util.List;
import java.util.function.Function;

import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.ProductView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.OrderDao;
import persistence.TransportServiceDao;
import persistence.impl.OrderDaoJpa;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class OrderController {


    private final OrderDaoJpa orderDao;
    private final TransportServiceDao transportServiceDao;

    public OrderController(OrderDao orderDao,TransportServiceDao transportServiceDao) {
        this.orderDao =  (OrderDaoJpa) orderDao;
        this.transportServiceDao = transportServiceDao;
    }
    
    public ObservableList<OrderView> getOrderListForUser (int userId) {
    	return FXCollections.observableArrayList(orderDao.getAllForUser(userId).stream().map(OrderView::new).toList());
    }

    public ObservableList<OrderView> getOrderByIdView(int id) {
        return FXCollections.observableArrayList(new OrderView(orderDao.get(id)));
    }

    public Order getOrderById(int id) {
        return orderDao.get(id);
    }

//    public ObservableList<ProductView> getProductsList(int orderId) {
//    	//TODO de juiste producten rechtreeks via een querrie opvragen
//        Order order = orderDao.get(orderId);
//        return FXCollections.observableArrayList(order.getProductsList()
//                .stream()
//                .collect(groupingBy(Function.identity(), counting()))
//                .entrySet()
//                .stream()
//                .map(entry -> new ProductView(entry.getKey(), entry.getValue().intValue()))
//                .toList());
//    }
    
    public ObservableList<ProductView> getProductsList(int orderId) {
    	//TODO de juiste producten rechtreeks via een querrie opvragen
    	List<OrderLine> list = orderDao.getOrderLinesForOrder(orderId);
        return FXCollections.observableArrayList(list.stream().map(el->new ProductView(el.getProduct(),el.getCount())).toList());
    }

    public void processOrder(int orderId, String transportServiceName) throws EntityNotFoundException, OrderStatusException {
        Order order = orderDao.get(orderId);
        TransportService transportService =  transportServiceDao.get(transportServiceName);
        if (!order.getStatus().equals(Status.POSTED))
            throw new OrderStatusException("Order must have status POSTED in order to get processed!");

        order.setTransportService(transportService);
        order.setStatus(Status.PROCESSED);
        order.addNotification(new Notification(order));
        order.generateTrackingCode();

        orderDao.update(order);
    }
}