package domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="conactpersons_supplier")
@NamedQueries({
	@NamedQuery(
			name="ContactPersonSupplier.findAllForSupplier",
			query="SELECT w FROM ContactPersonSupplier w WHERE w.supplier.email = ?1"
	)
})
public class ContactPersonSupplier {
	
	private String name;
	@Id
	private String email;
	@ManyToOne
	private Supplier supplier;
	
	public ContactPersonSupplier(String name, String email, Supplier supplier) {
		this.setName(name);
		this.setEmail(email);
		this.setSupplier(supplier);
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		if (supplier==null)
			throw new IllegalArgumentException("supplier may not be null!");
		this.supplier = supplier;
	}

	protected ContactPersonSupplier() {
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
		ContactPersonSupplier other = (ContactPersonSupplier) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public String toString() {
		return "ContactPersonSupplier [name=" + name + ", email=" + email + ", supplier=" + supplier + "]";
	}

	
	
	
	
}
