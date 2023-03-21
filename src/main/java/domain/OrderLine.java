package domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class OrderLine {
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
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
	
	
	
}
