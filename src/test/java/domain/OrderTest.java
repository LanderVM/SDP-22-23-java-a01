package domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.OrderDao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    EntityManager entityManager;

    @Mock
    Query query;

    private OrderDao orderDao;

    @Test
    public void getOrder_returnsCorrectOrder() {
        Order order = new Order("Testing BV", "Tes", "tes@mail.com", "Tessa 24 1000 Brussel", new Date(), List.of(new Product("Test product 1", 10.30), new Product("Test product 2", 9.80)), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM);

        when(entityManager.createNamedQuery("Order.findById")).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        orderDao = new OrderDao(entityManager);

        assertSame(order, orderDao.get(1).orElse(null));
        verify(query).setParameter(1, 1);
    }
}
