package gui.view;

import domain.ContactPersonSupplier;
import javafx.beans.property.SimpleStringProperty;

public class ContactPersonSupplierView {
	
	private final SimpleStringProperty email;
	private final SimpleStringProperty name;
	
	public ContactPersonSupplierView (ContactPersonSupplier contactPersonSupplier) {
		email = new SimpleStringProperty(contactPersonSupplier.getEmail());
		name = new SimpleStringProperty(contactPersonSupplier.getName());
	}

	public SimpleStringProperty getEmail() {
		return email;
	}

	public SimpleStringProperty getName() {
		return name;
	}
	
	
}
