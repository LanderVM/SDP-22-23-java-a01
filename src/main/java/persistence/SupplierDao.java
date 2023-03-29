package persistence;

import domain.Order;
import domain.Supplier;

import java.util.List;

public interface SupplierDao extends GenericDao<Supplier> {

    Supplier get(String email);

    List<Supplier> getCustomersForSupplier(int supplierId);

    List<Order> getOrdersForCustomer(String customerMail);
}
