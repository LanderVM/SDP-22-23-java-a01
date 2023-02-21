package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import domain.Products;
import util.JPAUtil_Orders;

public class ProductMapper {
	
	public ProductMapper() {
	}
	
	public List<Products> getOrders() {
		
		try {
			EntityManagerFactory emf = JPAUtil_Orders.getEntityManagerFactory();
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			
			List<Products> ProductsList;
			TypedQuery<Products> query = em.createNamedQuery("Products.findAll", Products.class);
			ProductsList = query.getResultList();
			
			em.getTransaction().commit();
			
			em.close();
			emf.close();
			
			return ProductsList;
			
		} catch (Exception e) {
			System.err.println(e);
		}  
		
		return null;
		
	}
	
	
	
}
