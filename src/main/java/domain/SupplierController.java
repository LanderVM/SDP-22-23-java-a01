package domain;

import java.util.List;

import gui.view.CustomerOrdersView;
import gui.view.CustomerView;
import gui.view.OrderView;
import jakarta.persistence.NoResultException;
import persistence.SupplierJPADao;

public class SupplierController {
	
	private SupplierJPADao supplierJPADao;
	
	public SupplierController(SupplierJPADao supplierJPADao) {
		this.supplierJPADao = supplierJPADao;
	}

	public Supplier getSupplier(int id) throws NoResultException {
		return supplierJPADao.get(id);
	}
	
	public Supplier getSupplier(String email) throws NoResultException {
		return supplierJPADao.get(email);
	}
	
	public List<CustomerView> getSuppliersView() {
		return supplierJPADao.getAll().stream().map(CustomerView::new).toList();
	}
	
	public List<CustomerOrdersView> getCustomerOrderView(String name) {
		return supplierJPADao.get(name).getOrdersAsCustomer().stream().map(CustomerOrdersView::new).toList();
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
