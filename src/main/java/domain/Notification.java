package domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "order_notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private int notificationId;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Supplier customer;
    @Column(name = "notification_date")
    private LocalDate date;
    @Column(name = "status")
    private String status;
    @Column(name = "message")
    private String message;

    protected Notification() {
    }

    public Notification(Order order, LocalDate date, String status, String message) {
        this.setOrder(order);
        this.setDate(date);
        this.status = status;
        this.message = message;
        this.customer = order.getCustomer();
    }

    public Notification(Order order) {
        this(order, LocalDate.now(), "new", "Your order has been processed.");
        this.customer = order.getCustomer();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("order may not be null!");
        this.order = order;
    }

    public void setDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("date may not be null!");
        this.date = date;
    }


}
