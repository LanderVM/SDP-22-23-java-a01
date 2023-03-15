package gui.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import domain.Order;
import domain.Packaging;
import domain.Status;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderView {

    private final SimpleIntegerProperty orderId;
    private final SimpleStringProperty company;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<BigDecimal> totalPrice;
    private final SimpleObjectProperty<Status> status;
    private final SimpleObjectProperty<Packaging> packagingProperty;
    private final SimpleStringProperty transportServiceProperty;
    private final SimpleIntegerProperty trackingCodeProperty;

    public OrderView(Order order) {
        orderId = new SimpleIntegerProperty(order.getOrderId());
        company = new SimpleStringProperty(order.getCustomer().getName());
        date = new SimpleObjectProperty<>(order.getDate());
        totalPrice = new SimpleObjectProperty<>(order.getOriginalAcquisitionPrice());
        status = new SimpleObjectProperty<>(order.getStatus());
        packagingProperty = new SimpleObjectProperty<>(order.getPackaging());
        transportServiceProperty = new SimpleStringProperty(order.getTransportService() == null ?  "None" : order.getTransportService().getName());
        trackingCodeProperty = new SimpleIntegerProperty(order.getTrackingCode());
    }

    public int getOrderId() {
        return orderId.get();
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public String getCompany() {
        return company.get();
    }

    public SimpleStringProperty companyProperty() {
        return company;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleObjectProperty<BigDecimal> totalPriceProperty() {
        return totalPrice;
    }

    public Status getStatus() {
        return status.get();
    }

    public SimpleObjectProperty<Status> statusProperty() {
        return status;
    }
    
    public SimpleObjectProperty<Packaging> packagingProperty() {
    	return packagingProperty;
    }
    
    public SimpleStringProperty transportServiceProperty() {
    	return transportServiceProperty;
    }
    
    public SimpleIntegerProperty trackingCodeProperty() {
    	return trackingCodeProperty;
    }
}
