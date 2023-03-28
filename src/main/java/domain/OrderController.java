package domain;

import java.util.List;
import java.util.stream.Collectors;

import exceptions.EntityDoesntExistException;
import exceptions.OrderStatusException;
import gui.view.OrderView;
import gui.view.ProductView;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.OrderDao;
import persistence.TransportServiceDao;

public class OrderController {


    private final OrderDao orderDao;
    private final TransportServiceDao transportServiceDao;
    private ObservableList<OrderView> orderList = FXCollections.emptyObservableList();

    public OrderController(OrderDao orderDao,TransportServiceDao transportServiceDao) {
        this.orderDao = orderDao;
        this.transportServiceDao = transportServiceDao;
    }

    public ObservableList<OrderView> getOrderList(int userId, boolean postedOnly) {
        if (postedOnly)
            this.orderList = FXCollections.observableList(orderDao.getAllForUserPosted(userId).stream().map(OrderView::new).collect(Collectors.toList()));
        else
            this.orderList = FXCollections.observableList(orderDao.getAllForUser(userId).stream().map(OrderView::new).collect(Collectors.toList()));
        return this.orderList;
    }

    public ObservableList<OrderView> getOrderByIdView(int id) {
        return FXCollections.observableArrayList(new OrderView(orderDao.get(id)));
    }

    public Order getOrderById(int orderId) {
        return orderDao.get(orderId);
    }

    public Supplier getCustomerForOrder(int orderId) {
		return orderDao.getCustomerForOrder(orderId);
	}

    public ObservableList<ProductView> getProductsList(int orderId) {
    	List<OrderLine> list = orderDao.getOrderLinesForOrder(orderId);
        return FXCollections.observableArrayList(list.stream().map(el->new ProductView(el.getProduct(),el.getCount())).toList());
    }

    public void processOrder(int orderId, String transportServiceName,int supplierId) throws EntityNotFoundException, OrderStatusException, EntityDoesntExistException {
        Order order = orderDao.get(orderId);
        if (order==null)
        	throw new EntityDoesntExistException("There is no order for given orderId!");
        TransportService transportService =  transportServiceDao.getForSupplier(transportServiceName, supplierId);
        if (transportService==null)
        	throw new EntityDoesntExistException("There is no transport service for given transportServiceName and supplierId!");
        if (!order.getStatus().equals(Status.POSTED))
            throw new OrderStatusException("Order must have status POSTED in order to get processed!");

        order.setTransportService(transportService);
        order.setStatus(Status.PROCESSED);
        order.addNotification(new Notification(order));
        order.generateTrackingCode();

        orderDao.update(order);
        orderList.set(getIndex(order.getOrderId()), new OrderView(order));
    }

    private int getIndex(int orderId) {
        List<OrderView> correspondingDTOs = orderList.stream().filter(dto -> dto.getOrderId() == orderId).toList();
        if (correspondingDTOs.isEmpty())
            throw new IllegalArgumentException("There is no OrderDTO matching this orderId!");
        if (correspondingDTOs.size() > 1)
            throw new RuntimeException("There were multiple OrderDTO found matching this orderId!");
        return orderList.indexOf(correspondingDTOs.get(0));
    }

}