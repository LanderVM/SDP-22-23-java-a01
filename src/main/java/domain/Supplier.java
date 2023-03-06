package domain;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="suppliers")
@NamedQueries({
		@NamedQuery(
                name = "Supplier.findByEmail",
                query = "SELECT w FROM Supplier w WHERE w.email = ?1"
        ),
        @NamedQuery(
                name = "Supplier.findAll",
                query = "SELECT d FROM Supplier d"
        ),
        @NamedQuery(
                name = "Supplier.findAllWithOrdersAsCustomer",
                query = "SELECT d FROM Supplier d WHERE d.ordersAsCustomer IS NOT EMPTY"
        )
})
public class Supplier {
	
	private String name;
	
	@Id
	private String email;
	
	private String adress;
	
	@Column(name="telephone_number")
	private int telephoneNumber;
	
	@Lob
	private byte[] logo;
	
	@OneToMany(mappedBy="supplier")
	private List<Order> ordersAsSupplier;
	
	@OneToMany(mappedBy="customer")
	private List<Order> ordersAsCustomer;

	public Supplier(String name, String email, String adress, int telephoneNumber,byte[] logo, List<Order> ordersAsSupplier,
			List<Order> ordersAsCustomer) {
		super();
		this.name = name;
		this.email = email;
		this.adress = adress;
		this.telephoneNumber = telephoneNumber;
		this.logo = logo;
		this.ordersAsSupplier = ordersAsSupplier;
		this.ordersAsCustomer = ordersAsCustomer;
	}
	public Supplier(String name, String email, String adress, int telephoneNumber,byte[] logo) {
		super();
		this.name = name;
		this.email = email;
		this.adress = adress;
		this.telephoneNumber = telephoneNumber;
		this.logo = logo;
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(int telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
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

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		return Objects.equals(email, other.email);
	}
	
	
	
}
