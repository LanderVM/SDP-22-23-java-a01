package domain;

import java.util.List;

import gui.view.ContactPersonSupplierView;
import gui.view.CustomerOrdersView;
import gui.view.CustomerView;
import jakarta.persistence.NoResultException;
import persistence.ContactPersonSupplierJPADao;
import persistence.OrderDao;
import persistence.SupplierDao;

public class SupplierController {

	private final SupplierDao supplierDao;
	private final OrderDao orderDao;
	private ContactPersonSupplierJPADao contactPersonSupplierJPADao;

	public SupplierController(SupplierDao supplierDao, OrderDao orderDao, ContactPersonSupplierJPADao contactPersonSupplierJPADao) {
		this.supplierDao = supplierDao;
		this.orderDao = orderDao;
		this.contactPersonSupplierJPADao = contactPersonSupplierJPADao;
	}

	public Supplier getSupplier(String email) throws NoResultException {
		return supplierDao.get(email);
	}

	public List<CustomerView> getSuppliersView(int supplierId) {
		return orderDao.getAllForSupplier(supplierId).stream().map(Order::getCustomer).distinct().map(CustomerView::new).toList();
//         TODO hier geen ordersDAO gebruiken, moet het opvragen via OrderController
	}

	public List<CustomerOrdersView> getCustomerOrderView(String mail) {
		return supplierDao.get(mail).getOrdersAsCustomer().stream().map(CustomerOrdersView::new).toList();
	}

	public List<ContactPersonSupplierView> getContactPersonSupplierView(String supplierEmail) {
		return contactPersonSupplierJPADao.getAllForSupplier(supplierEmail).stream().map(ContactPersonSupplierView::new).toList();
	}
}
