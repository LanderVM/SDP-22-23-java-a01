package domain;

import java.util.List;

import gui.view.ContactPersonSupplierView;
import gui.view.CustomerOrdersView;
import gui.view.CustomerView;
import gui.view.OrderView;
import jakarta.persistence.NoResultException;
import persistence.ContactPersonSupplierJPADao;
import persistence.OrderJPADao;
import persistence.SupplierJPADao;

public class SupplierController {
	
	private SupplierJPADao supplierJPADao;
	private OrderJPADao ordersJPADao;
	private ContactPersonSupplierJPADao contactPersonSupplierJPADao;
	
	public SupplierController(SupplierJPADao supplierJPADao,OrderJPADao orderJPADao,ContactPersonSupplierJPADao contactPersonSupplierJPADao) {
		this.supplierJPADao = supplierJPADao;
		this.ordersJPADao = orderJPADao;
		this.contactPersonSupplierJPADao = contactPersonSupplierJPADao;
	}

	public Supplier getSupplier(int id) throws NoResultException {
		return supplierJPADao.get(id);
	}
	
	public Supplier getSupplier(String email) throws NoResultException {
		return supplierJPADao.get(email);
	}
	
	public List<CustomerView> getSuppliersView(int supplierId) {
		return ordersJPADao.getAllForSupplier(supplierId).stream().map(Order::getCustomer).distinct().map(CustomerView::new).toList();
	}
	
	public List<CustomerOrdersView> getCustomerOrderView(String name) {
		return supplierJPADao.get(name).getOrdersAsCustomer().stream().map(CustomerOrdersView::new).toList();
	}
	
	public List<ContactPersonSupplierView> getContactPersonSupplierView (String supplierEmail) {
		return contactPersonSupplierJPADao.getAllForSupplier(supplierEmail).stream().map(ContactPersonSupplierView::new).toList();
	}
	
	public List<Supplier> getSuppliers() {
		return supplierJPADao.getAll();
	}
	
	public List<Supplier> getCustomers() {
		return supplierJPADao.getCustomers();
	}
	
	public List<Supplier> getCustomersFilteredByName() {
		return supplierJPADao.getAllCustomersOrderedByName();
	}
	
}
