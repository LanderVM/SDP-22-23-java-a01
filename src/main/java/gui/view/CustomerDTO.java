package gui.view;

import domain.Supplier;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerDTO {

    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phoneNumber;
    private final SimpleIntegerProperty numberOfOrders;
    private final SimpleObjectProperty<byte[]> logo;
    
    
    public CustomerDTO(Supplier supplier, int openOrders) {
       email = new SimpleStringProperty(supplier.getEmail());
       name = new SimpleStringProperty(supplier.getName());
       address = new SimpleStringProperty(supplier.getAddress());
       phoneNumber = new SimpleStringProperty(supplier.getPhoneNumber());
		numberOfOrders = new SimpleIntegerProperty(openOrders);
       logo = new SimpleObjectProperty<>(supplier.getLogo());
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
