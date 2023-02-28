package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUserUtil {
    private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("user");

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    private JPAUserUtil() {
    }
}
