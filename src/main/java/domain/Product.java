package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "productdata")
@NamedQueries({@NamedQuery(name = "Products.findAll", query = "SELECT d FROM Product d")})
public class Product implements Serializable {
    // UC attributes: name, amount, individualPrice, totalPrice

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;

    private int syncId;
    private String unitOfMeasureId;
    private String productCategoryId;
    private String productAvailability;

    public Product(int syncId, String unitOfMeasureId, String productCategoryId, String productAvailability) {
        this.syncId = syncId;
        this.unitOfMeasureId = unitOfMeasureId;
        this.productCategoryId = productCategoryId;
        this.productAvailability = productAvailability;
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.syncId = syncId;
    }

    public String getUnitOfMeasureId() {
        return unitOfMeasureId;
    }

    public void setUnitOfMeasureId(String unitOfMeasureId) {
        this.unitOfMeasureId = unitOfMeasureId;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(String productAvailability) {
        this.productAvailability = productAvailability;
    }

    @Override
    public String toString() {
        return "Products [productId=" + productId + ", syncId=" + syncId + ", unitOfMeasureId=" + unitOfMeasureId + ", productCategoryId=" + productCategoryId + ", productAvailability=" + productAvailability + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Product other = (Product) obj;
        return Objects.equals(productId, other.productId);
    }


}
