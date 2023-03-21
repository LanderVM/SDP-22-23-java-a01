package domain;

import java.io.Serializable;
import java.util.Objects;

public class OrderLineKey implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int orderId;
	
	private int productId;

	public OrderLineKey(int orderId, int productId) {
		super();
		this.orderId = orderId;
		this.productId = productId;
	}

	protected OrderLineKey() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLineKey other = (OrderLineKey) obj;
		return orderId == other.orderId && productId == other.productId;
	}

	
	
	
	
}
