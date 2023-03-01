package gui.view;

import domain.Status;
import domain.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class OrderView {

    private SimpleIntegerProperty idForTable;
    private SimpleStringProperty companyForTable;
    private SimpleObjectProperty<Date> dateForTable;
    private SimpleObjectProperty<Double> costForTable;
    private SimpleObjectProperty<Status> statusForTable;

    public OrderView(Order order) {
        idForTable = new SimpleIntegerProperty(order.getOrderId());
        companyForTable = new SimpleStringProperty(order.getCompany());
        dateForTable = new SimpleObjectProperty<>(order.getDate());
        costForTable = new SimpleObjectProperty<>(10.25);
        statusForTable = new SimpleObjectProperty<>(order.getStatus());
    }

    public int getIdForTable() {
        return idForTable.get();
    }

    public SimpleIntegerProperty idForTableProperty() {
        return idForTable;
    }

    public SimpleStringProperty getCompanyForTable() {
        return companyForTable;
    }

    public SimpleStringProperty companyForTableProperty() {
        return companyForTable;
    }

    public Date getDateForTable() {
        return dateForTable.get();
    }

    public SimpleObjectProperty<Date> dateForTableProperty() {
        return dateForTable;
    }

    public double getCostForTable() {
        return costForTable.get();
    }

    public SimpleObjectProperty<Double> costForTableProperty() {
        return costForTable;
    }

    public Status getStatusForTable() {
        return statusForTable.get();
    }

    public SimpleObjectProperty<Status> statusForTableProperty() {
        return statusForTable;
    }
}
