package testMain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import application.StartUp;
import domain.Order;
import domain.OrderLine;
import domain.Product;
import domain.Supplier;
import persistence.OrderDao;
import persistence.impl.OrderDaoJpa;
import persistence.impl.SupplierDaoJpa;
import util.JPAUtil;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartUp.seedDatabase();
		
		OrderDao od = new OrderDaoJpa(JPAUtil.getEntityManagerFactory().createEntityManager());
		
		List<OrderLine> lijst = od.getOrderLinesForOrder(1);
		System.out.println(lijst);
	}

}
