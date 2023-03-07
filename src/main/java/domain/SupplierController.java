package domain;

import java.util.List;

import jakarta.persistence.NoResultException;
import persistence.SupplierJPADao;

public class SupplierController {
	
	private SupplierJPADao supplierJPADao;
	
	public SupplierController(SupplierJPADao supplierJPADao) {
		this.supplierJPADao = supplierJPADao;
	}

	public Supplier getSupplierById(String id) throws NoResultException {
		return supplierJPADao.get(id);
	}
	
	public Supplier getSupplierByEmail(String email) throws NoResultException {
		return supplierJPADao.getByMail(email);
	}
	
	public List<Supplier> getAllSuppliers() {
		return supplierJPADao.getAll();
	}
	
	public List<Supplier> getAllWithOrderAsCustomer() {
		return supplierJPADao.getAllWithOrdersAsCustomer();
	}
	
}
