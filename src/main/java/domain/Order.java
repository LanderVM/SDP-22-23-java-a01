package domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Entity
@Table(name = "customer_order")
@NamedQueries({
        @NamedQuery(
                name = "Order.findById",
                query = "SELECT w FROM Order w WHERE w.orderId = ?1"
        ),
        @NamedQuery(
                name = "Order.findAll",
                query = "SELECT d FROM Order d WHERE d.supplier.supplierId = ?1"
        ),
        @NamedQuery(
                name = "Order.findAllForSupplier",
                query = "SELECT d FROM Order d WHERE d.supplier.supplierId = ?1" // TODO
        ),
        @NamedQuery(
                name = "Order.findAllForUser",
                query = "SELECT d FROM Order d WHERE d.supplier.supplierId = (SELECT w.supplier.supplierId FROM User w WHERE w.userId = ?1)"
        ),
        @NamedQuery(
                name = "Order.getOrderLinesForOrder",
                query = "SELECT d.orderLines FROM Order d WHERE d.orderId = ?1"

        ),
        @NamedQuery(
                name = "Order.getCustomerForOrder",
                query = "SELECT d.customer FROM Order d WHERE d.orderId = ?1"
        ),
        @NamedQuery(
                name = "Order.findAllForUserPosted",
                query = "SELECT d FROM Order d WHERE d.status = domain.Status.POSTED AND d.supplier.supplierId = (SELECT w.supplier.supplierId FROM User w WHERE w.userId = ?1)"
        ),
        @NamedQuery(
                name = "Order.findAllForUserOpen",
                query = "SELECT d FROM Order d WHERE d.status != domain.Status.DELIVERED AND d.customer.supplierId = ?1"
        ),
        @NamedQuery(
                name = "Order.getUserDataForProcessedMail",
                query = "SELECT NEW gui.view.OrderTrackingMailDTO(d.customer.email, " +
                        "d.trackingCode, " +
                        "CASE WHEN d.carrier.trackingCodeDetails.verificationType = 'POST_CODE' then d.delivery_postal_code " +
                        "WHEN d.carrier.trackingCodeDetails.verificationType = 'ORDER_ID' then d.orderId ELSE '' END) " +
                        "FROM Order d WHERE d.orderId = ?1"
        )
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "delivery_country")
    private String delivery_country = "";
    @Column(name = "delivery_city")
    private String delivery_city = "";
    @Column(name = "delivery_postal_code")
    private String delivery_postal_code = "";
    @Column(name = "delivery_street")
    private String delivery_street = "";
    @Column(name = "delivery_house_number")
    private Integer delivery_house_number = 0;
    @Column(name = "delivery_box")
    private String delivery_box ="";
    @Column(name = "order_date")
    private LocalDate date;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderLine> orderLines;
    @Column(name = "order_status")
    private Status status;
    @ManyToOne
    private Packaging packaging;
    @ManyToOne
    private Carrier carrier;
    @Column(name = "tracking_code")
    private String trackingCode;
    @ManyToOne
    private Supplier supplier;
    @ManyToOne
    private Supplier customer;
    @OneToMany(mappedBy = "order")
    private Set<Notification> notifications = new HashSet<>();


    public Order(LocalDate date, String delivery_country, String delivery_city, String delivery_postal_code, String delivery_street, Integer delivery_house_number, String delivery_box, List<Product> productsList, Status status,
                 Carrier carrier, Packaging packaging, Supplier supplier, Supplier customer, BigDecimal originalAcquisitionPrice) {
        this(date, delivery_country, delivery_city, delivery_postal_code, delivery_street, delivery_house_number, delivery_box, productsList, status, carrier, packaging);
        this.setSupplier(supplier);
        this.setCustomer(customer);
    }

    public Order(LocalDate date, String delivery_country, String delivery_city, String delivery_postal_code, String delivery_street, Integer delivery_house_number, String delivery_box, List<Product> productsList, Status status,
                 Carrier carrier, Packaging packaging) {
    	this.delivery_country = delivery_country;
        this.delivery_city = delivery_city;
        this.delivery_postal_code = delivery_postal_code;
        this.delivery_street = delivery_street;
        this.delivery_house_number = delivery_house_number;
        this.delivery_box = delivery_box;
        this.setDate(date);
        this.setStatus(status);
        this.setCarrier(carrier);
        this.setPackaging(packaging);
        this.orderLines = new ArrayList<>();
        makeOrderlines(productsList);
    }

    protected Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException("date may not be null!");
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null)
            throw new IllegalArgumentException("status may not be null!");
        this.status = status;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public void setPackaging(Packaging packagingType) {
        if (packagingType == null)
            throw new IllegalArgumentException("Packaging must not be null!");
        this.packaging = packagingType;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
    
    public BigDecimal getOriginalAcquisitionPrice() {
        return orderLines.stream().map(OrderLine::getOriginalAcquisitionPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void generateTrackingCode() {
        SecureRandom secureRandom = new SecureRandom();
        TrackingCodeDetails trackingCodeDetails = getCarrier().getTrackingCodeDetails();

        int leftLimit = 48;
        int rightLimit = trackingCodeDetails.isIntegersOnly() ? 57 : 90;
        int targetLength = trackingCodeDetails.getCharacterCount();
        String prefix = trackingCodeDetails.getPrefix();

        trackingCode = prefix +
                secureRandom.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65))
                        .limit(targetLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getCustomer() {
        return customer;
    }

    public void setCustomer(Supplier customer) {
        this.customer = customer;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        if (!notification.getOrder().equals(this))
            throw new RuntimeException("Notification doesn't belong to this order");
        notifications.add(notification);
    }

    public void makeOrderlines(List<Product> productsList) {
        if (productsList == null)
            throw new IllegalArgumentException("productsList may not be null!");
        if (productsList.isEmpty())
            throw new IllegalArgumentException("productsList may not be empty!");
        List<List<Product>> list = new ArrayList<>();
        Map<Object, List<Product>> map = productsList.stream().collect(Collectors.groupingBy(Product::getName));
        for (Entry<Object, List<Product>> e : map.entrySet()) {
            list.add(e.getValue());
        }
        for (List<Product> l : list) {
            orderLines.add(new OrderLine(l, this));
        }

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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", company='" + supplier.getName() + '\'' +
                ", customerName='" + customer.getName() + '\'' +
                ", customerEmail='" + customer.getEmail() + '\'' +
                ", address='" + customer.getAddress() + '\'' +
                ", date=" + date +
                ", orderLines=" + orderLines +
                ", status=" + status +
                ", packaging=" + packaging +
                ", carrier=" + carrier +
                ", trackingCode=" + trackingCode +
                '}';
    }
}