package domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(
                name = "Order.findById",
                query = "SELECT d FROM Orders d WHERE order_id = ?1"
        ),
        @NamedQuery(
                name = "Order.findAll",
                query = "SELECT d FROM Orders d"
        )
})
public class Order {

    @Transient
    private SimpleIntegerProperty idForTable;
    @Transient
    private SimpleStringProperty companyForTable;
    @Transient
    private SimpleStringProperty dateForTable;

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
    @ManyToMany
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

        idForTable = new SimpleIntegerProperty();
        idForTable.set(orderId);
        companyForTable = new SimpleStringProperty();
        companyForTable.set(company);
        dateForTable = new SimpleStringProperty();
        dateForTable.set(date.toString());
    }

    protected Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public IntegerProperty idForTableProperty() {
        return this.idForTable;
    }

    public StringProperty companyForTableProperty() {
        return this.companyForTable;
    }

    public StringProperty dateForTableProperty() {
        return this.dateForTable;
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
                "idForTable=" + idForTable +
                ", companyForTable=" + companyForTable +
                ", dateForTable=" + dateForTable +
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

    public Date getDate() {
        return date;
    }
}