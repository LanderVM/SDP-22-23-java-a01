package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private final static EntityManagerFactory ORDERS_ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("orders");

    public static EntityManagerFactory getOrdersEntityManagerFactory() {
        return ORDERS_ENTITY_MANAGER_FACTORY;
    }

    private JPAUtil() {
    }
}