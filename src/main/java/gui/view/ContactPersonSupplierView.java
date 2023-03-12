package gui.view;

import domain.ContactPersonSupplier;
import javafx.beans.property.SimpleStringProperty;

public class ContactPersonSupplierView {
	
	private SimpleStringProperty email;
	private SimpleStringProperty name;
	
	public ContactPersonSupplierView (ContactPersonSupplier contactPersonSupplier) {
		email = new SimpleStringProperty(contactPersonSupplier.getEmail());
		name = new SimpleStringProperty(contactPersonSupplier.getName());
	}

	public SimpleStringProperty getEmail() {
		return email;
	} // TODO getters consistency between views

	public SimpleStringProperty getName() {
		return name;
	}
	
	
}
