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
    @Column(name = "order_date")
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
