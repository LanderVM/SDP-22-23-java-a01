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

    public Supplier getCustomer() {
        return customer;
    }

    public void setCustomer(Supplier customer) {
        this.customer = customer;
    }

    protected Notification() {
    }

    public Notification(Order order, LocalDate date) {
        this.order = order;
        this.date = date;
        this.customer = order.getCustomer();
    }

    public Notification(Order order) {
        this(order, LocalDate.now());
        this.customer = order.getCustomer();
    }

    public int getNotificationId() {
        return notificationId;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDate getDate() {
        return date;
    }

}
