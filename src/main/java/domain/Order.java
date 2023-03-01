package domain;

import java.util.Date;
import java.util.List;

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

        if (trackingCode != order.trackingCode) return false;
        if (!company.equals(order.company)) return false;
        if (!customerName.equals(order.customerName)) return false;
        if (!customerEmail.equals(order.customerEmail)) return false;
        if (!address.equals(order.address)) return false;
        if (!date.equals(order.date)) return false;
        if (!productsList.equals(order.productsList)) return false;
        if (status != order.status) return false;
        if (packaging != order.packaging) return false;
        return transportService == order.transportService;
    }

    // TODO
    public void setTransportService(TransportService transportService) {
        this.transportService = transportService;
    }

    @Override
    public int hashCode() {
        int result = company.hashCode();
        result = 31 * result + customerName.hashCode();
        result = 31 * result + customerEmail.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + productsList.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + packaging.hashCode();
        result = 31 * result + transportService.hashCode();
        result = 31 * result + trackingCode;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", orderId=" + orderId +
                ", company='" + company + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", products=" + productsList +
                ", status=" + status +
                ", packaging=" + packaging +
                ", transportService=" + transportService +
                ", trackingCode=" + trackingCode +
                '}';
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(int trackingCode) {
        this.trackingCode = trackingCode;
    }

    public Date getDate() {
        return date;
    }
}