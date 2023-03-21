package testMain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import application.StartUp;
import domain.Order;
import domain.Product;
import domain.Supplier;
import persistence.impl.SupplierDaoJpa;
import util.JPAUtil;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartUp.seedDatabase();
		
//		Product product1 = new Product("test_product een", new BigDecimal("1.00"));
//        Product product2 = new Product("test_product twee", new BigDecimal("2.00"));
//        Product product3 = new Product("test_product drie", new BigDecimal("4.50"));
//        Product product4 = new Product("test_product vier", new BigDecimal("8.90"));
//        Product product5 = new Product("test_product vijf", new BigDecimal("11.30"));
//        
//        List<Product> list = List.of(product1,product3,product3,product2,product1,product2,product3,product2);
//        Map<Object, List<Product>>map = list.stream().collect(Collectors.groupingBy(el->el.getName()));
//        List<List<Product>> list2 = new ArrayList();
//        for (Entry<Object, List<Product>> e:map.entrySet()) {
//        	list2.add(e.getValue());
//        }
//        System.out.println(list2);
	}

}
