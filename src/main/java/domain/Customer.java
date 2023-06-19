package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    protected Customer(){}

    public Customer(String auth0id, String email, String username, String profilePictureUrl, Supplier supplier, Supplier company) {
        this.auth0id = auth0id;
        this.email = email;
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
        this.supplier = supplier;
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int customerId;

    @Column(name = "auth0_id")
    private String auth0id;

    @Column(name = "email")
    private String email = "";
    @Column(name = "username")
    private String username = "";
    @Column(name = "image_URL")
    private String profilePictureUrl = "";
    @OneToOne
    @JoinColumn(name = "SUPPLIER_supplier_id")
    private Supplier supplier;
    @OneToOne
    @JoinColumn(name = "supplier_id")
    private Supplier company;
}
