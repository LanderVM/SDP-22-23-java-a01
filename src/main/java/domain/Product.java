package domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @Column(name = "name")
    private String name = "";
    @Column(name = "price")
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        setPrice(price);
    }

    protected Product() {
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        if (price == null) throw new IllegalArgumentException("price may not be null!");
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("price may not be negative!");
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}
