package gui.view;

import domain.Status;
import domain.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class CustomerOrdersView {

    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<Status> status;
    
    public CustomerOrdersView(Order order) {
       id = new SimpleIntegerProperty(order.getOrderId());
       date = new SimpleObjectProperty<>(order.getDate());
       status = new SimpleObjectProperty<>(order.getStatus());
       
    }

	public SimpleIntegerProperty getId() {
		return id;
	} // TODO consistency between getters view

	public SimpleObjectProperty<LocalDate> getDate() {
		return date;
	}

	public SimpleObjectProperty<Status> getStatus() {
		return status;
	}

}

