package domain;

import java.util.List;

import gui.view.ContactPersonSupplierView;
import gui.view.CustomerOrdersView;
import gui.view.CustomerView;
import jakarta.persistence.NoResultException;
import persistence.ContactPersonSupplierJPADao;
import persistence.OrderDao;
import persistence.SupplierJPADao;

public class SupplierController {
	
	private SupplierJPADao supplierJPADao;
	private final OrderDao orderDao;
	private ContactPersonSupplierJPADao contactPersonSupplierJPADao;
	
	public SupplierController(SupplierJPADao supplierJPADao, OrderDao orderDao, ContactPersonSupplierJPADao contactPersonSupplierJPADao) {
		this.supplierJPADao = supplierJPADao;
		this.orderDao = orderDao;
		this.contactPersonSupplierJPADao = contactPersonSupplierJPADao;
	}

	public Supplier getSupplier(int id) throws NoResultException {
		return supplierJPADao.get(id);
	}
	
	public Supplier getSupplier(String email) throws NoResultException {
		return supplierJPADao.get(email);
	}
	
	public List<CustomerView> getSuppliersView(int supplierId) {
		return orderDao.getAllForSupplier(supplierId).stream().map(Order::getCustomer).distinct().map(CustomerView::new).toList(); // TODO dit geeft een probleem? Wie heeft dit geschreven?
		// TODO hier geen ordersDAO gebruiken, moet het opvragen via OrderController
	}
	
	public List<CustomerOrdersView> getCustomerOrderView(String mail) {
		return supplierJPADao.get(mail).getOrdersAsCustomer().stream().map(CustomerOrdersView::new).toList();
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
