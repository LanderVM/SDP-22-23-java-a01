package gui.view;

import domain.Product;
import domain.Status;
import domain.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.util.Date;

public class OrderView {

    private final SimpleIntegerProperty orderId;
    private final SimpleStringProperty company;
    private final SimpleObjectProperty<Date> date;
    private final SimpleObjectProperty<BigDecimal> totalPrice;
    private final SimpleObjectProperty<Status> status;

    public OrderView(Order order) {
        orderId = new SimpleIntegerProperty(order.getOrderId());
        company = new SimpleStringProperty(order.getCompany());
        date = new SimpleObjectProperty<>(order.getDate());
        totalPrice = new SimpleObjectProperty<>(order.getProductsList().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        status = new SimpleObjectProperty<>(order.getStatus());
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

    public Date getDate() {
        return date.get();
    }

    public SimpleObjectProperty<Date> dateProperty() {
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
}
