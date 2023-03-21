package persistence;

import domain.Order;
import domain.OrderLine;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    List<Order> getAllForSupplier(int supplierId);
    
    List<OrderLine> getOrderLinesForOrder (int orderId);
}
