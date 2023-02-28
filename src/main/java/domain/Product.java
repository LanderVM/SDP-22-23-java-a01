package domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({@NamedQuery(name = "Product.findAll", query = "SELECT d FROM Product d")})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    private String name;
    private double price;

    Product(String name, double price) {
        setName(name);
        setPrice(price);
    }

    protected Product() {
    }

    public int getProductId() {
        return productId;
    }

    void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}
