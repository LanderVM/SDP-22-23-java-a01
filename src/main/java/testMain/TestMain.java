package testMain;

import java.util.List;

import application.StartUp;
import domain.Order;
import domain.Supplier;
import persistence.impl.SupplierDaoJpa;
import util.JPAUtil;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartUp.seedDatabase();
		SupplierDaoJpa sdj = new SupplierDaoJpa(JPAUtil.getEntityManagerFactory().createEntityManager());
		
		List<Supplier> lijst = sdj.getCustomersForSupplier(2);
		
		for (Supplier s:lijst) {
			System.out.println(s.toString());
			System.out.println("*****");
		}
		//List<Order> lijst2 = sdj.getOrdersForCustomer("jan@mail.com");
		//System.out.println(lijst2);
	}

}
