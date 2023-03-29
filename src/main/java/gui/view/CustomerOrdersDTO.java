package gui.view;

import domain.Status;
import domain.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class CustomerOrdersDTO {

    private final SimpleIntegerProperty orderPropertyId;
    private final SimpleObjectProperty<LocalDate> orderPropertyDate;
    private final SimpleObjectProperty<Status> orderPropertyStatus;

    public CustomerOrdersDTO(Order order) {
        orderPropertyId = new SimpleIntegerProperty(order.getOrderId());
        orderPropertyDate = new SimpleObjectProperty<>(order.getDate());
        orderPropertyStatus = new SimpleObjectProperty<>(order.getStatus());
    }

    public SimpleIntegerProperty getOrderPropertyId() {
        return orderPropertyId;
    }

    public SimpleObjectProperty<LocalDate> getOrderPropertyDate() {
        return orderPropertyDate;
    }

    public SimpleObjectProperty<Status> getOrderPropertyStatus() {
        return orderPropertyStatus;
    }
}

