package domain;

import java.math.BigDecimal;
import java.util.*;

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
        ),
        @NamedQuery(
        		name = "Order.findAllPostedForSupplier",
                query = "SELECT d FROM Order d WHERE d.status = domain.Status.POSTED AND d.supplier.supplierId = ?1"
        ),
        @NamedQuery(
        		name = "Order.findAllForSupplier",
                query = "SELECT d FROM Order d WHERE d.supplier.supplierId = ?1"
        )
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    private String address;

    @Column(name = "order_date")
    private Date date;
    @ManyToMany(mappedBy = "orders")
    private List<Product> productsList;
    private Status status;
    private Packaging packaging;

    @ManyToOne
    private TransportService transportService;

    @Column(name = "tracking_code")
    private int trackingCode;
    
    @ManyToOne
    private Supplier supplier;
    
    @ManyToOne
    private Supplier customer;

    @Column(name = "original_acquisition_price")
    private BigDecimal originalAcquisitionPrice;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Notification> notifications = new HashSet<>();


    public Order(Date date, List<Product> productsList, Status status,
                 TransportService transportService, Packaging packaging,Supplier supplier,Supplier customer,BigDecimal originalAcquisitionPrice) {
        this.date = date;
        this.productsList = productsList;
        this.status = status;
        this.transportService = transportService;
        this.packaging = packaging;
        this.originalAcquisitionPrice = originalAcquisitionPrice;
        this.supplier=supplier;
        this.customer=customer;
    }
    
    public Order(Date date, List<Product> productsList, Status status,
            TransportService transportService, Packaging packaging) {
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

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public void setPackaging(Packaging packaging) {
        this.packaging = packaging;
    }

    public TransportService getTransportService() {
        return transportService;
    }

    public void setTransportService(TransportService transportService) {
        this.transportService = transportService;
    }

    public int getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(int trackingCode) {
        this.trackingCode = trackingCode;
    }

    public BigDecimal getOriginalAcquisitionPrice() {
        return originalAcquisitionPrice;
    }

    public void setOriginalAcquisitionPrice(BigDecimal originalAcquisitionPrice) {
        this.originalAcquisitionPrice = originalAcquisitionPrice;
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

    public void addNotification(Notification notification) {
        if (!notification.getOrder().equals(this))
            throw new RuntimeException("Notification doesn't belong to this order"); // TODO testing & proper exception
        notifications.add(notification);
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
                ", productsList=" + productsList +
                ", status=" + status +
                ", packaging=" + packaging +
                ", transportService=" + transportService +
                ", trackingCode=" + trackingCode +
                ", originalAcquisitionPrice=" + originalAcquisitionPrice +
                '}';
    }
}