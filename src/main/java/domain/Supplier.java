package domain;

import jakarta.persistence.*;
import logoMapper.LogoMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "supplier")
@NamedQueries({
        @NamedQuery(
                name = "Supplier.findByEmail",
                query = "SELECT w FROM Supplier w WHERE w.email = ?1"
        ),
        @NamedQuery(
                name = "Supplier.findById",
                query = "SELECT w FROM Supplier w WHERE w.supplierId = ?1"
        ),
        @NamedQuery(
                name = "Supplier.findByName",
                query = "SELECT s FROM Supplier s WHERE s.name = ?1"
        ),
        @NamedQuery(
                name = "Supplier.findAll",
                query = "SELECT d FROM Supplier d"
        ),
        @NamedQuery(
                name = "Supplier.findAllCustomersForSupplier",
                query = "SELECT DISTINCT c FROM Supplier s JOIN s.ordersAsSupplier o JOIN o.customer c WHERE s.supplierId = ?1"
        ),
        @NamedQuery(
                name = "Supplier.getAllOrdersForCustomer",
                query = "SELECT c.ordersAsCustomer FROM Supplier c WHERE c.email = ?1"
        )
})
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int supplierId = -1;
    @Column(name = "name")
    private String name = "";
    @Column(name = "supplier_email")
    private String email = "";
    @Column(name = "delivery_street")
    private String delivery_street = "";
    @Column(name = "delivery_box")
    private String delivery_box ="";
    @Column(name = "delivery_city")
    private String delivery_city = "";
    @Column(name = "delivery_country")
    private String delivery_country = "";
    @Column(name = "delivery_house_number")
    private Integer delivery_house_number = 0;
    @Column(name = "delivery_postal_code")
    private String delivery_postal_code = "";
    
    @Column(name = "phone_number")
    private String phoneNumber = "";
    @OneToOne(mappedBy = "supplier", cascade = CascadeType.PERSIST)
    private Logo logo = LogoMapper.makeLogo("/images/testImg.jpg", this);
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.PERSIST)
    private List<ContactPersonSupplier> contactPersons = Collections.emptyList();
    @OneToMany(mappedBy = "supplier")
    private List<User> users = Collections.emptyList();
    @OneToMany(mappedBy = "supplier")
    private List<Order> ordersAsSupplier = Collections.emptyList();
    @OneToMany(mappedBy = "customer")
    private List<Order> ordersAsCustomer = Collections.emptyList();
    @OneToMany(mappedBy = "supplier")
    private List<Carrier> carriers = Collections.emptyList();

    public Supplier(String name, String email, String address, String phoneNumber, String logoLocation, List<Order> ordersAsSupplier,
                    List<Order> ordersAsCustomer, List<ContactPersonSupplier> contactPersons, List<User> users) {
        this(name, email, address, phoneNumber, logoLocation);
        this.setOrdersAsSupplier(ordersAsSupplier);
        this.setOrdersAsCustomer(ordersAsCustomer);
        this.setContactPersons(contactPersons);
        this.setUsers(users);
    }

    public Supplier(String name, String email, String address, String phoneNumber, String logoLocation) {
        this.name = name;
        this.email = email;
        this.delivery_country = address;
        this.phoneNumber = phoneNumber;
        this.logo = LogoMapper.makeLogo(logoLocation, this);
    }

    protected Supplier() {
    }

    Supplier(String name) {
        setName(name);
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return delivery_country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public byte[] getLogo() {
        return logo.getLogo();
    }

    public void setOrdersAsSupplier(List<Order> ordersAsSupplier) {
        if (ordersAsSupplier == null)
            throw new IllegalArgumentException("ordersAsSupplier may not be null!");
        this.ordersAsSupplier = ordersAsSupplier;
    }

    public void setOrdersAsCustomer(List<Order> ordersAsCustomer) {
        if (ordersAsCustomer == null)
            throw new IllegalArgumentException("ordersAsCustomer may not be null!");
        this.ordersAsCustomer = ordersAsCustomer;
    }

    public void setContactPersons(List<ContactPersonSupplier> contactPersons) {
        if (contactPersons == null)
            throw new IllegalArgumentException("contactPersons may not be null!");
        this.contactPersons = contactPersons;
    }

    public void setUsers(List<User> users) {
        if (users == null)
            throw new IllegalArgumentException("users may not be null!");
        this.users = users;
    }

    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return supplierId == supplier.supplierId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId);
    }

    @Override
    public String toString() {
        return "Supplier [supplierId=" + supplierId + ", name=" + name + ", email=" + email + " ]";
    }

}
