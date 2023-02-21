package domain;

import java.util.List;

import repository.ProductMapper;

public class DomainController {

	private List<Products> orders;
	
	public DomainController() {
		orders = new ProductMapper().getOrders();
	}
	
	public List<Products> generateListOrders() {
		return orders;
	}
	
}
