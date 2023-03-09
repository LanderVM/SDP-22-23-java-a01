package gui.view;

import domain.Product;
import domain.Status;
import domain.Supplier;
import domain.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerVieuw {

    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleIntegerProperty phoneNumber;
    private final SimpleIntegerProperty numberOfOrders;

    public CustomerVieuw(Supplier supplier) {
       email = new SimpleStringProperty(supplier.getEmail());
       name = new SimpleStringProperty(supplier.getName());
       address = new SimpleStringProperty(supplier.getAddress());
       phoneNumber = new SimpleIntegerProperty(supplier.getPhoneNumber());
       numberOfOrders = new SimpleIntegerProperty(supplier.getOrdersAsCustomer().stream().filter(order -> order.getStatus() != Status.DELIVERED).toList().size());
       
    }

	public SimpleStringProperty getEmail() {
		return email;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public SimpleStringProperty getAddress() {
		return address;
	}

	public SimpleIntegerProperty getPhoneNumber() {
		return phoneNumber;
	}
	
	public SimpleIntegerProperty getNumberOfOrders() {
		return numberOfOrders;
	}

}
