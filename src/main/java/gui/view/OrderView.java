package gui.view;

import domain.Product;
import domain.Status;
import domain.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class OrderView {

    private SimpleIntegerProperty orderId;
    private SimpleStringProperty company;
    private SimpleObjectProperty<Date> date;
    private SimpleObjectProperty<Double> totalPrice;
    private SimpleObjectProperty<Status> status;

    public OrderView(Order order) {
        orderId = new SimpleIntegerProperty(order.getOrderId());
        company = new SimpleStringProperty(order.getCompany());
        date = new SimpleObjectProperty<>(order.getDate());
        totalPrice = new SimpleObjectProperty<>(Math.round(order.getProductsList().stream().mapToDouble(Product::getPrice).sum() * 100.0) / 100.0);
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

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleObjectProperty<Double> totalPriceProperty() {
        return totalPrice;
    }

    public Status getStatus() {
        return status.get();
    }

    public SimpleObjectProperty<Status> statusProperty() {
        return status;
    }
}
