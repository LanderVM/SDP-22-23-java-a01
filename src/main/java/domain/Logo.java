package domain;

import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="logo")
public class Logo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logo_id")
	private int id;

	@Lob
    private byte[] logo = new byte[0];
	
	@OneToOne
	private Supplier supplier;

	public Logo(byte[] logo, Supplier supplier) {
		super();
		this.logo = logo;
		this.setSupplier(supplier);
	}

	protected Logo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		if(supplier==null)
			throw new IllegalArgumentException("supplier may not be null!");
		this.supplier = supplier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logo other = (Logo) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Logo [id=" + id + ", logo=" + Arrays.toString(logo) + ", supplier=" + supplier + "]";
	}
	
	
	
}
