package domain;

import java.util.List;

import gui.view.ContactPersonSupplierView;
import gui.view.CustomerOrdersView;
import gui.view.CustomerView;
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

	public ObservableList<CustomerView> getSuppliersView(int supplierId) {
		return FXCollections.observableArrayList(orderDao.getAllForSupplier(supplierId).stream().map(Order::getCustomer).distinct().map(CustomerView::new).toList());
//         TODO hier geen ordersDAO gebruiken, moet het opvragen via OrderController
		//TODO gebruiken van getCustomersForSupplier uit supplierDao, maar werkt nog niet
	}

	public ObservableList<CustomerOrdersView> getCustomerOrderView(String mail) {
		return FXCollections.observableArrayList(supplierDao.getOrdersForCustomer(mail).stream().map(CustomerOrdersView::new).toList());
	}

	public ObservableList<ContactPersonSupplierView> getContactPersonSupplierView(String supplierEmail) {
		return FXCollections.observableArrayList(contactPersonSupplierDao.getAllForSupplier(supplierEmail).stream().map(ContactPersonSupplierView::new).toList());
	}
}
