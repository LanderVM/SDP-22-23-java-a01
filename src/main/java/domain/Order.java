package domain;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
        ),
        @NamedQuery(
        		name="Order.findAllForUser",
        		query = "SELECT d FROM Order d WHERE d.supplier.supplierId = (SELECT w.supplier.supplierId FROM User w WHERE w.userId = ?1)"
        ),
        @NamedQuery(
        		name = "Order.getOrderLinesForOrder", 
        		query = "SELECT d.orderLines FROM Order d WHERE d.orderId = ?1"
        		
        )
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    private String address;

    @Column(name = "order_date")
    private LocalDate date;
    
    @OneToMany(mappedBy="order",cascade=CascadeType.PERSIST)
    private List<OrderLine> orderLines;
    
    private Status status;
    private Packaging packaging;

    @ManyToOne
    private TransportService transportService;

    @Column(name = "tracking_code")
    private String trackingCode;

    @ManyToOne
    private Supplier supplier;
    
    @ManyToOne
    private Supplier customer;

    @Column(name = "original_acquisition_price")
    private BigDecimal originalAcquisitionPrice;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Notification> notifications = new HashSet<>();


    public Order(LocalDate date, String adress, List<Product> productsList, Status status,
                 TransportService transportService, Packaging packaging, Supplier supplier, Supplier customer, BigDecimal originalAcquisitionPrice) {
        this.date = date;
        this.address = adress;
        this.status = status;
        this.transportService = transportService;
        this.packaging = packaging;
        this.originalAcquisitionPrice = originalAcquisitionPrice;
        this.supplier=supplier;
        this.customer=customer;
        this.orderLines = new ArrayList<>();
        makeOrderlines(productsList);
    }

	public Order(LocalDate date, List<Product> productsList, Status status,
            TransportService transportService, Packaging packaging) {
    	this.date = date;
    	this.status = status;
    	this.transportService = transportService;
    	this.packaging = packaging;
    	this.orderLines = new ArrayList<>();
    	makeOrderlines(productsList);
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getTrackingCode() {
        return trackingCode;
    }

    public void generateTrackingCode() {
        SecureRandom secureRandom = new SecureRandom();
        TrackingCodeDetails trackingCodeDetails = getTransportService().getTrackingCodeDetails();

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

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        if (!notification.getOrder().equals(this))
            throw new RuntimeException("Notification doesn't belong to this order"); // TODO testing & proper exception
        notifications.add(notification);
    }
    
    private void makeOrderlines(List<Product> productsList) {
		List<List<Product>> list = new ArrayList<>();
		Map<Object, List<Product>>map = productsList.stream().collect(Collectors.groupingBy(el->el.getName()));
        for (Entry<Object, List<Product>> e:map.entrySet()) {
        	list.add(e.getValue());
        }
        for (List<Product> l:list) {
        	orderLines.add(new OrderLine(l,this));
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
                ", transportService=" + transportService +
                ", trackingCode=" + trackingCode +
                ", originalAcquisitionPrice=" + originalAcquisitionPrice +
                '}';
    }
}