package domain;

import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public abstract class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    private String name;

    private String email;

    private String address;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Lob
    private byte[] logo;

    @OneToMany
    private List<Order> ordersList;

    public Customer(String name, String email, String address, int phoneNumber, byte[] logo, List<Order> ordersList) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.ordersList = ordersList;
    }

    protected Customer() {
    }


    public int getId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public byte[] getLogo() {
        return logo;
    }

    public List<Order> getOrders() {
        return ordersList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public void setOrders(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
