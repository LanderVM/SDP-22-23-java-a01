package domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.OrderJPADao;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    EntityManager entityManager;

    @Mock
    TypedQuery<Order> query;

    private OrderJPADao orderDao;

    @Test
    public void get_returnsCorrectOrder() {
        Order order = new Order("Testing BV", "Tes", "tes@mail.com", "Tessa 24 1000 Brussel", new Date(), List.of(new Product("Test product 1", 10.30), new Product("Test product 2", 9.80)), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM);

        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        orderDao = new OrderJPADao(entityManager);

        assertEquals(order, orderDao.get(1).orElse(null));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getAll_returnsAllOrders() {
        List<Order> ordersList =
                List.of(new Order("Testing BV", "Tes", "tes@mail.com", "Tessa 24 1000 Brussel", new Date(), List.of(new Product("Test product 1", 10.30), new Product("Test product 2", 9.80)), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM),
                        new Order("Tim CO", "Tim", "tim@mail.com", "Timlaan 24 1000 Brussel", new Date(), List.of(new Product("Test product 3", 7.40)), Status.PROCESSED, TransportService.POSTNL, Packaging.MEDIUM),
                        new Order("Jan INC", "Jan", "jan@mail.com", "Janstraat 12 9000 Aalst", new Date(), List.of(new Product("Test product 4", 1.20), new Product("Test product 5", 88.3)), Status.POSTED, TransportService.BPOST, Packaging.CUSTOM)
                );

        when(entityManager.createNamedQuery("Order.findAll", Order.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(ordersList);

        orderDao = new OrderJPADao(entityManager);

        assertEquals(ordersList, orderDao.getAll());
        verify(query).getResultList();
    }
}
