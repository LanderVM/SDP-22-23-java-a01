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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "packages")
public class Package implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private int packageId;
	
    @Column(unique=true)
	private String name;
    
	private Packaging type;
	private BigDecimal height;
	private BigDecimal width;
	private BigDecimal lenght;
	private BigDecimal price;
	private boolean active;
	
	public Package(String name, Packaging type, BigDecimal height, BigDecimal width, BigDecimal lenght,
			BigDecimal price, boolean active) {
		this.name = name;
		this.type = type;
		this.height = height;
		this.width = width;
		this.lenght = lenght;
		this.price = price;
		this.active = active;
	}
	
	public Package(String name, Packaging type, BigDecimal height, BigDecimal width, BigDecimal lenght,
			BigDecimal price) {
		this.name = name;
		this.type = type;
		this.height = height;
		this.width = width;
		this.lenght = lenght;
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

	public Packaging getType() {
		return type;
	}

	public void setType(Packaging type) {
		this.type = type;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getLenght() {
		return lenght;
	}

	public void setLenght(BigDecimal lenght) {
		this.lenght = lenght;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDimensions() {
		return String.format("%d x %d x %d", height, lenght, width);
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
				+ ", width=" + width + ", lenght=" + lenght + ", price=" + price + ", active=" + active + "]";
	}
	
}
