package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="order_lines")
public class OrderLine implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Order order;
	
	@Id
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Product product;
	
	private int count;
	
	
	public OrderLine (List<Product> products, Order order) {
		this.order = order;
		this.count = products.size();
		this.product = products.get(0);
	}
	
	public OrderLine (Product product, int count, Order order) {
		this.order = order;
		this.product = product;
		this.count = count;
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
	public int hashCode() {
		return Objects.hash(order, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		return Objects.equals(order, other.order) && Objects.equals(product, other.product);
	}

	@Override
	public String toString() {
		return "OrderLine [orderId=" + order.getOrderId() + ", productId=" + product.getProductId() + ", count=" + count + "]";
	}
	
	
	
}
