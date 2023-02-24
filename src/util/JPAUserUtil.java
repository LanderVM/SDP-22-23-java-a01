package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUserUtil {
	private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("user");

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	private JPAUserUtil() {
	}
}
