package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "packaging")
@NamedQueries({
		@NamedQuery(
				name = "Packaging.findById",
				query = "SELECT p FROM Packaging p WHERE p.packagingId = ?1"
		),
		@NamedQuery(
				name = "Packaging.findByName",
				query = "SELECT p FROM Packaging p WHERE p.name = ?1"
		),
		@NamedQuery(
				name = "Packaging.findAll",
				query = "SELECT p FROM Packaging p WHERE p.supplier.supplierId = ?1"
		),
		@NamedQuery(
				name = "Packaging.findNameExists",
				query = "SELECT p FROM Packaging p WHERE p.name = ?1 AND p.supplier.supplierId = ?2"
		),
})
public class Packaging implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packaging_id")
    private int packagingId;

    @Column(name = "name", unique = true)
	private String name;

	@Column(name = "packaging_type")
	private PackagingType type = PackagingType.STANDARD;

	@Column(name = "height")
	private double height = 0;

	@Column(name = "width")
	private double width = 0;

	@Column(name = "length")
	private double length = 0;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "is_active")
	private boolean active = true;

	@ManyToOne
	private Supplier supplier = new Supplier("UNKNOWN");

	public Packaging(String name, double width, double height, double length, double price, PackagingType type, boolean active, Supplier supplier) {
		setName(name);
        setWidth(width);
        setHeight(height);
        setLength(length);
		setPrice(price);
        setType(type);
		setActive(active);
        setSupplier(supplier);
    }

	protected Packaging() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty() || name.isBlank())
			throw new IllegalArgumentException("Packaging name must not be empty!");
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

	public void setPrice(double price) {
		if (price <= 0)
			throw new IllegalArgumentException("Packaging price must not be be 0 or negative");
		this.price = new BigDecimal(price);
	}

	public void setType(PackagingType type) {
		this.type = type;
	}

	public void setHeight(double height) {
		if (height < 0)
			throw new IllegalArgumentException("Packaging width must not be negative!");
		this.height = height;
	}

	public void setWidth(double width) {
		if (width < 0)
			throw new IllegalArgumentException("Packaging width must not be negative!");
		this.width = width;
	}

	public void setLength(double length) {
		if (length < 0)
			throw new IllegalArgumentException("Packaging width must not be negative!");
		this.length = length;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		if (supplier == null)
			throw new IllegalArgumentException("Packaging supplier must not be null!");
		this.supplier = supplier;
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
