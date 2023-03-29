package gui.view;

import domain.Supplier;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerDTO {

    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty numberOfOrders;

    public CustomerDTO(Supplier supplier, int openOrders) {
        email = new SimpleStringProperty(supplier.getEmail());
        name = new SimpleStringProperty(supplier.getName());
        numberOfOrders = new SimpleIntegerProperty(openOrders);
    }

    public SimpleStringProperty getEmail() {
        return email;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleIntegerProperty getNumberOfOrders() {
        return numberOfOrders;
    }
}
