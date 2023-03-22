package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "packages")
public class Package implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private int packageId;

    @Column(unique = true)
	private String name;

	private PackagingType type;
	private double height;
	private double width;
	private double length;
	private BigDecimal price;
	private boolean active;

	public Package(String name, PackagingType type, double height, double width, double length,
				   BigDecimal price, boolean active) {
		this.name = name;
		this.type = type;
		this.height = height;
		this.width = width;
		this.length = length;
		this.price = price;
		this.active = active;
	}

	public Package(String name, PackagingType type, double height, double width, double length,
				   BigDecimal price) {
		this.name = name;
		this.type = type;
		this.height = height;
		this.width = width;
		this.length = length;
		this.price = price;
		this.active = true;
	}

	protected Package() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(packageId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Package other = (Package) obj;
		return packageId == other.packageId;
	}

	@Override
	public String toString() {
		return "Package [packageId=" + packageId + ", name=" + name + ", type=" + type + ", height=" + height
				+ ", width=" + width + ", lenght=" + length + ", price=" + price + ", active=" + active + "]";
	}

}
