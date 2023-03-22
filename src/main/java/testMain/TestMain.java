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
import domain.TransportService;
import persistence.OrderDao;
import persistence.TransportServiceDao;
import persistence.impl.OrderDaoJpa;
import persistence.impl.SupplierDaoJpa;
import persistence.impl.TransportServiceDaoJpa;
import util.JPAUtil;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartUp.seedDatabase();
		
		TransportServiceDao tsd = new TransportServiceDaoJpa(JPAUtil.getEntityManagerFactory().createEntityManager());
		List<TransportService> lijst = tsd.getAllForSupplier(2);
		
		System.out.println(lijst);
	}

}
