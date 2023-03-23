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
                name = "Supplier.findByName",
                query = "SELECT s FROM Supplier s WHERE s.name = ?1"
        ),
        @NamedQuery(
                name = "Supplier.findAll", // TODO merge with OrderedByName
                query = "SELECT d FROM Supplier d"
        ),
        @NamedQuery(
                name = "Supplier.findAllWithOrdersAsCustomer",
                query = "SELECT d FROM Supplier d WHERE d.ordersAsCustomer IS NOT EMPTY"
        ),
        @NamedQuery(
        		name = "Supplier.findAllCustomersOrderedByName",
        		query = "SELECT s FROM Supplier s ORDER BY s.name"
        ),
        @NamedQuery(
        		name="Supplier.findAllCustomersForSupplier",
        		query="SELECT DISTINCT c FROM Supplier s JOIN s.ordersAsSupplier o JOIN o.customer c WHERE s.supplierId = ?1"
        ),
        @NamedQuery(
        		name ="Supplier.getAllOrdersForCustomer",
        		query ="SELECT c.ordersAsCustomer FROM Supplier c WHERE c.email = ?1"
        )
})
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int supplierId;

    private String name = "";

    private String email = "";

    private String address = "";

    @Column(name = "phone_number")
    private String phoneNumber = "";
    
    @OneToOne(mappedBy="supplier", cascade = CascadeType.PERSIST)
    private Logo logo;
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.PERSIST)
    private List<ContactPersonSupplier> contactPersons;
    
    @OneToMany(mappedBy="supplier")
    private List<User> users;

    @OneToMany(mappedBy = "supplier")
    private List<Order> ordersAsSupplier;

    @OneToMany(mappedBy = "customer")
    private List<Order> ordersAsCustomer;
    
    @OneToMany(mappedBy="supplier")
    private List<TransportService> transportServices;

    public Supplier(String name, String email, String address, String phoneNumber, String logoLocation, List<Order> ordersAsSupplier,
                    List<Order> ordersAsCustomer,List<ContactPersonSupplier> contactPersons,List<User> users) {
        this(name,email,address,phoneNumber,logoLocation);
        this.setOrdersAsSupplier(ordersAsSupplier);
        this.setOrdersAsCustomer(ordersAsCustomer);
        this.setContactPersons(contactPersons);
        this.setUsers(users);
    }

    public Supplier(String name, String email, String address, String phoneNumber, String logoLocation) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logo = LogoMapper.makeLogo(logoLocation, this);
    }

    protected Supplier() {
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
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getLogo() {
    	return logo.getLogo();
    }
    
    public List<Order> getOrdersAsSupplier() {
        return ordersAsSupplier;
    }

    public void setOrdersAsSupplier(List<Order> ordersAsSupplier) {
    	if(ordersAsSupplier == null)
    		throw new IllegalArgumentException("ordersAsSupplier may not be null!");
        this.ordersAsSupplier = ordersAsSupplier;
    }

    public List<Order> getOrdersAsCustomer() {
        return ordersAsCustomer;
    }
    
    public void setOrdersAsCustomer(List<Order> ordersAsCustomer) {
    	if(ordersAsCustomer==null)
    		throw new IllegalArgumentException("ordersAsCustomer may not be null!");
        this.ordersAsCustomer = ordersAsCustomer;
    }
    
    

    public List<ContactPersonSupplier> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<ContactPersonSupplier> contactPersons) {
		if (contactPersons==null)
			throw new IllegalArgumentException("contactPersons may not be null!");
		this.contactPersons = contactPersons;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		if(users==null)
			throw new IllegalArgumentException("users may not be null!");
		this.users = users;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}

	public List<TransportService> getTransportServices() {
		return transportServices;
	}

	public void setTransportServices(List<TransportService> transportServices) {
		this.transportServices = transportServices;
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
		return "Supplier [supplierId=" + supplierId + ", name=" + name + ", email=" + email +" ]";
	}
    
}
