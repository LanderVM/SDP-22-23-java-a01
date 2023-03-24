package gui.view;

import domain.User;
import javafx.beans.property.SimpleStringProperty;

public class UserDTO {

    private final SimpleStringProperty name;
    private final SimpleStringProperty adress;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty emailAddress;
    private final SimpleStringProperty role;

    public UserDTO(User user) {
        this.name = new SimpleStringProperty(String.format("%s %s", user.getName(), user.getSurname()));
        this.adress = new SimpleStringProperty(user.getAddress());
        this.phoneNumber = new SimpleStringProperty(user.getPhoneNumber());
        this.emailAddress = new SimpleStringProperty(user.getEmailAddress());
        this.role = new SimpleStringProperty(user.isAdmin() ? "Admin" : "Warehouseman");
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getAdress() {
        return adress.get();
    }

    public SimpleStringProperty adressProperty() {
        return adress;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public SimpleStringProperty emailAddressProperty() {
        return emailAddress;
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }
}
