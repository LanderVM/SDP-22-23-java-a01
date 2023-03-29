package domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_line")
public class OrderLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private int orderLineId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Order order;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Product product;
    @Column(name = "product_count")
    private int count;

    public OrderLine(List<Product> products, Order order) {
        if (products == null) throw new IllegalArgumentException("products may not be null!");
        if (products.isEmpty()) throw new IllegalArgumentException("products may not be empty!");
        this.setOrder(order);
        this.count = products.size();
        this.product = products.get(0);
    }

    protected OrderLine() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("order may not be null!");
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return orderLineId == orderLine.orderLineId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderLineId);
    }

    @Override
    public String toString() {
        return "OrderLine [orderId=" + order.getOrderId() + ", productId=" + product.getProductId() + ", count=" + count + "]";
    }


}
