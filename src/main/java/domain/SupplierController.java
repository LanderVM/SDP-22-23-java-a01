package domain;

import gui.view.ContactPersonSupplierDTO;
import gui.view.CustomerDTO;
import gui.view.CustomerOrdersDTO;
import jakarta.persistence.NoResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.ContactPersonSupplierDao;
import persistence.OrderDao;
import persistence.SupplierDao;

public class SupplierController {

    private final SupplierDao supplierDao;
    private final OrderDao orderDao;
    private final ContactPersonSupplierDao contactPersonSupplierDao;

    public SupplierController(SupplierDao supplierDao, OrderDao orderDao, ContactPersonSupplierDao contactPersonSupplierDao) {
        this.supplierDao = supplierDao;
        this.orderDao = orderDao;
        this.contactPersonSupplierDao = contactPersonSupplierDao;
    }

    public Supplier getSupplier(String email) throws NoResultException {
        return supplierDao.get(email);
    }

    public ObservableList<CustomerDTO> getSuppliersView(int supplierId) {
        return FXCollections.observableArrayList(supplierDao.getCustomersForSupplier(supplierId).stream().map(supplier -> new CustomerDTO(supplier, orderDao.getAllForUserOpen(supplier.getSupplierId()).size())).toList());
    }

    public ObservableList<CustomerOrdersDTO> getCustomerOrderView(String mail) {
        return FXCollections.observableArrayList(supplierDao.getOrdersForCustomer(mail).stream().map(CustomerOrdersDTO::new).toList());
    }

    public ObservableList<ContactPersonSupplierDTO> getContactPersonSupplierView(String supplierEmail) {
        return FXCollections.observableArrayList(contactPersonSupplierDao.getAllForSupplier(supplierEmail).stream().map(ContactPersonSupplierDTO::new).toList());
    }
}
