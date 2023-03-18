package persistence;

import java.util.Collections;
import java.util.List;

import domain.Product;
import jakarta.persistence.EntityManager;

public class ProductJPADao implements JPADao<Product,Integer>{
	
	EntityManager entityManager;

    public ProductJPADao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@Override
	public Product get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Product> getProductsForOrder (int orderId) {
		return Collections.unmodifiableList(entityManager.createNamedQuery("Product.findAllForOrder",Product.class).setParameter(1, orderId).getResultList());
	}

	@Override
	public List getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		
	}

}
