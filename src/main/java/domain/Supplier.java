package domain;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import logoMapper.LogoMapper;

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
                name = "Supplier.findAll",
                query = "SELECT d FROM Supplier d"
        ),
        @NamedQuery(
                name = "Supplier.findAllWithOrdersAsCustomer",
                query = "SELECT d FROM Supplier d WHERE d.ordersAsCustomer IS NOT EMPTY"
        ),
        @NamedQuery(
        		name = "Supplier.findAllCustomersOrderedByName",
        		query = "SELECT s FROM Supplier s ORDER BY s.name"
        )
})
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int supplierId;

    private String name;

    private String email;

    private String address;

    @Column(name = "phone_number")
    private int phoneNumber;
    
    @OneToOne(mappedBy="supplier", cascade = CascadeType.PERSIST)
    private Logo logo;
    
    @OneToMany
    private List<ContactPersonSupplier> contactPersons;
    
    @OneToMany(mappedBy="supplier")
    private List<User> users;

    @OneToMany(mappedBy = "supplier")
    private List<Order> ordersAsSupplier;

    @OneToMany(mappedBy = "customer")
    private List<Order> ordersAsCustomer;

    public Supplier(String name, String email, String address, int phoneNumber, byte[] logo, List<Order> ordersAsSupplier,
                    List<Order> ordersAsCustomer,List<ContactPersonSupplier> contactPersons,List<User> users) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logo = LogoMapper.makeLogo(logo, this);
        this.ordersAsSupplier = ordersAsSupplier;
        this.ordersAsCustomer = ordersAsCustomer;
        this.contactPersons = contactPersons;
        this.users = users;
    }

    public Supplier(String name, String email, String address, int phoneNumber, byte[] logo) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logo = LogoMapper.makeLogo(logo, this);
    }

    protected Supplier() {
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
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getLogo() {
    	return logo.getLogo();
    }
    
    public List<Order> getOrdersAsSupplier() {
        return ordersAsSupplier;
    }

    public void setOrdersAsSupplier(List<Order> ordersAsSupplier) {
        this.ordersAsSupplier = ordersAsSupplier;
    }

    public List<Order> getOrdersAsCustomer() {
        return ordersAsCustomer;
    }
    
    public void setOrdersAsCustomer(List<Order> ordersAsCustomer) {
        this.ordersAsCustomer = ordersAsCustomer;
    }
    
    

    public List<ContactPersonSupplier> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<ContactPersonSupplier> contactPersons) {
		this.contactPersons = contactPersons;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
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
}
