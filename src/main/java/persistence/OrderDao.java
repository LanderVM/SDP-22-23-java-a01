package persistence;

import domain.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    List<Order> getAllForSupplier(int supplierId);
}
