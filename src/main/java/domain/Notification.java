package domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private int notificationId;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Supplier customer;

    private LocalDate date;

    protected Notification() {
    }

    public Notification(Order order, LocalDate date) {
        this.setOrder(order);
        this.setDate(date);
        this.customer = order.getCustomer();
    }

    public Notification(Order order) {
        this(order, LocalDate.now());
        this.customer = order.getCustomer();
    }

    public int getNotificationId() {
        return notificationId;
    }

    public Supplier getCustomer() {
        return customer;
    }

    public void setCustomer(Supplier customer) {
        this.customer = customer;
    }
    
    public Order getOrder() {
        return order;
    }
    

    public void setOrder(Order order) {
    	if(order==null)
    		throw new IllegalArgumentException("order may not be null!");
		this.order = order;
	}

	public LocalDate getDate() {
        return date;
    }

	public void setDate(LocalDate date) {
		if (date==null)
			throw new IllegalArgumentException("date may not be null!");
		this.date = date;
	}
	

}
