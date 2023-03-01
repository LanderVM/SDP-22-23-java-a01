package domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(
                name = "Order.findById",
                query = "SELECT w FROM Order w WHERE w.orderId = ?1"
        ),
        @NamedQuery(
                name = "Order.findAll",
                query = "SELECT d FROM Order d"
        ),
        @NamedQuery(
                name = "Order.findAllPosted",
                query = "SELECT d FROM Order d WHERE d.status = domain.Status.POSTED"
        )
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "company_name")
    private String company;
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    private String address;

    @Column(name = "order_date")
    private Date date;
    @OneToMany
    private List<Product> productsList;
    private Status status;
    private Packaging packaging;
    @Column(name = "transport_service")
    private TransportService transportService;

    @Column(name = "tracking_code")
    private int trackingCode;


    public Order(String company, String customerName, String customerEmail, String address, Date date, List<Product> productsList, Status status,
                 TransportService transportService, Packaging packaging) {
        this.company = company;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.address = address;
        this.date = date;
        this.productsList = productsList;
        this.status = status;
        this.transportService = transportService;
        this.packaging = packaging;
    }

    protected Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}