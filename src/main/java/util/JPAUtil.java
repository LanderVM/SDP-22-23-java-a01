package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private final static EntityManagerFactory ORDERS_ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("orders");

    public static EntityManagerFactory getOrdersEntityManagerFactory() {
        return ORDERS_ENTITY_MANAGER_FACTORY;
    }

    private JPAUtil() {
    }
}