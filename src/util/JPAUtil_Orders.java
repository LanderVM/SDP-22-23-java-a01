package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil_Orders {
	private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orders");

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	private JPAUtil_Orders() {
	}
}