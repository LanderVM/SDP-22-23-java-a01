package main;

import domain.DomainController;
import repository.ProductMapper;

public class startUp {

	public static void main(String[] args) {
	
		DomainController dc = new DomainController();
		
		dc.generateListOrders().forEach(e -> System.out.println(e));
		
	}

}
