package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name="order_lines")
public class OrderLine implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_line_id")
	private int orderLineId;

	@ManyToOne(cascade=CascadeType.PERSIST)
	private Order order;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Product product;
	
	private int count;
	
	
	public OrderLine (List<Product> products, Order order) {
		this.order = order;
		this.count = products.size();
		this.product = products.get(0);
	}

	protected OrderLine() {
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
