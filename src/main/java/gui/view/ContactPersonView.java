package gui.view;

import domain.ContactPerson;
import javafx.beans.property.SimpleStringProperty;

public class ContactPersonView {

    private final SimpleStringProperty email;
    private final SimpleStringProperty phoneNumber;

    public ContactPersonView(ContactPerson contactPerson) {
        email = new SimpleStringProperty(contactPerson.getEmail());
        phoneNumber = new SimpleStringProperty(contactPerson.getPhoneNumber());
    }
    
    public ContactPersonView(String email,String phoneNumber) {
    	this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }
}
