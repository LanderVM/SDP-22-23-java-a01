package gui.view;

import domain.Product;
import domain.Status;
import domain.Supplier;
import domain.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerOrdersView {

    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<Date> date;
    private final SimpleObjectProperty<Status> status;
    
    public CustomerOrdersView(Order order) {
       id = new SimpleIntegerProperty(order.getOrderId());
       date = new SimpleObjectProperty<Date>(order.getDate());
       status = new SimpleObjectProperty<Status>(order.getStatus());
       
    }

	public SimpleIntegerProperty getId() {
		return id;
	}

	public SimpleObjectProperty<Date> getDate() {
		return date;
	}

	public SimpleObjectProperty<Status> getStatus() {
		return status;
	}

}

