package domain;

import java.util.List;

import gui.view.CustomerVieuw;
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
	
	public List<CustomerVieuw> getSuppliersView() {
		return supplierJPADao.getAll().stream().map(CustomerVieuw::new).toList();
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
