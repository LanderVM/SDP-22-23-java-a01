package persistence;

import java.util.List;

import domain.Order;
import domain.Supplier;

public interface SupplierDao extends GenericDao<Supplier> {

    Supplier get(String email);
    
    List<Supplier> getCustomersForSupplier (int supplierId);
    
    List<Order> getOrdersForCustomer (String customerMail);
}
