package gui.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import domain.Order;
import domain.Status;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderDTO {

    private final SimpleIntegerProperty orderId;
    private final SimpleStringProperty company;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<BigDecimal> totalPrice;
    private final SimpleObjectProperty<Status> status;
    private final SimpleStringProperty packaging;
    private final SimpleStringProperty carrier;
    private final SimpleStringProperty trackingCode;

    public OrderDTO(Order order) {
        orderId = new SimpleIntegerProperty(order.getOrderId());
        company = new SimpleStringProperty(order.getCustomer().getName());
        date = new SimpleObjectProperty<>(order.getDate());
        totalPrice = new SimpleObjectProperty<>(order.getOriginalAcquisitionPrice());
        status = new SimpleObjectProperty<>(order.getStatus());
        packaging = new SimpleStringProperty(order.getPackaging().getName());
        carrier = new SimpleStringProperty(order.getCarrier() == null ? "None" : order.getCarrier().getName());
        trackingCode = new SimpleStringProperty(order.getTrackingCode());
    }

    public int getOrderId() {
        return orderId.get();
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public SimpleStringProperty companyProperty() {
        return company;
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public SimpleObjectProperty<BigDecimal> totalPriceProperty() {
        return totalPrice;
    }

    public Status getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return new SimpleStringProperty(status.get().toString());
    }

    public SimpleStringProperty packagingProperty() {
        return packaging;
    }

    public SimpleStringProperty carrierProperty() {
        return carrier;
    }

    public SimpleStringProperty trackingCodeProperty() {
        return trackingCode;
    }
}
