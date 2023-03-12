package gui.view;

import domain.Status;
import domain.Supplier;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerView {

    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phoneNumber;
    private final SimpleIntegerProperty numberOfOrders;
    private final SimpleObjectProperty<byte[]> logo;

    public CustomerView(Supplier supplier) {
       email = new SimpleStringProperty(supplier.getEmail());
       name = new SimpleStringProperty(supplier.getName());
       address = new SimpleStringProperty(supplier.getAddress());
       phoneNumber = new SimpleStringProperty(supplier.getPhoneNumber());
       numberOfOrders = new SimpleIntegerProperty(supplier.getOrdersAsCustomer().stream().filter(order -> order.getStatus() != Status.DELIVERED).toList().size()); // TODO mag geen filters gebruiken, moet vanuit databank. bekijken
       logo = new SimpleObjectProperty<byte[]>(supplier.getLogo());
    }

	public SimpleStringProperty getEmail() {
		return email;
	} // TODO consistency between getters view

	public SimpleStringProperty getName() {
		return name;
	}

	public SimpleStringProperty getAddress() {
		return address;
	}

	public SimpleStringProperty getPhoneNumber() {
		return phoneNumber;
	}
	
	public SimpleIntegerProperty getNumberOfOrders() {
		return numberOfOrders;
	}
	
	public SimpleObjectProperty<byte[]> getLogo() {
		return logo;
	}

}
