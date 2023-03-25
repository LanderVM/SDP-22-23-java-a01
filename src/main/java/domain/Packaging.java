package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.*;
import persistence.impl.SupplierDaoJpa;
import util.JPAUtil;

@Entity
@Table(name = "packages")
@NamedQueries({
		@NamedQuery(
				name = "Supplier.findById",
				query = "SELECT p FROM Packaging p WHERE p.packagingId = ?1"
		),
		@NamedQuery(
				name = "Supplier.findByName",
				query = "SELECT p FROM Packaging p WHERE p.name = ?1"
		),
		@NamedQuery(
				name = "Supplier.findAll",
				query = "SELECT p FROM Packaging p WHERE p.supplier.supplierId = ?1"
		),
})
public class Packaging implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packaging_id")
    private int packagingId;

    @Column(unique = true)
	private String name;

	private PackagingType type = PackagingType.STANDARD;
	private double height = 0;
	private double width = 0;
	private double length = 0;
	private BigDecimal price;
	private boolean active = true;

	@ManyToOne
	private final Supplier supplier  = new SupplierDaoJpa(JPAUtil.getEntityManagerFactory().createEntityManager()).get(1); // TODO

	public Packaging(String name, PackagingType type, double height, double width, double length,
					 BigDecimal price, boolean active) {

		this( name, type,  height,  width,  length, price);
		this.active = active;
	}

	public Packaging(String name, PackagingType type, double height, double width, double length,
					 BigDecimal price) {
		this.name = name;
		this.type = type;
		this.height = height;
		this.width = width;
		this.length = length;
		this.setPrice(price);
		this.active = true;
	}

	protected Packaging() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPackagingId() {
		return packagingId;
	}

	public PackagingType getType() {
		return type;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getLength() {
		return length;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public boolean isActive() {
		return active;
	}

	public void setPrice(BigDecimal price) {
		if (price == null)
			throw new IllegalArgumentException("price may not be null!");
		if(price.compareTo(BigDecimal.ZERO)<0)
			throw new IllegalArgumentException("price may not be negative!");
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(packagingId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Packaging other = (Packaging) obj;
		return packagingId == other.packagingId;
	}

	@Override
	public String toString() {
		return "Package [packageId=" + packagingId + ", name=" + name + ", type=" + type + ", height=" + height
				+ ", width=" + width + ", lenght=" + length + ", price=" + price + ", active=" + active + "]";
	}

}
