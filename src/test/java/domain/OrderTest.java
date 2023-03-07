package domain;

import exceptions.OrderStatusException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.OrderJPADao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    EntityManager entityManager;

    @Mock
    TypedQuery<Order> query;
    @Mock
    EntityTransaction entityTransaction;

    private OrderJPADao orderDao;

    private Order order;
    private NewTransportService transportService;

    private void mockFindAllPosted() {
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);
    }

    @Test
    public void getById_happyFlow() {
        transportService = new NewTransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
        order = new Order("Testing BV", "Tes", "tes@mail.com", "Tessa 24 1000 Brussel", new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, transportService, Packaging.MEDIUM, new BigDecimal("20.10"));

        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        orderDao = new OrderJPADao(entityManager);

        assertEquals(order, orderDao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getById_invalidID_throwsNoResultException() {
        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        orderDao = new OrderJPADao(entityManager);

        assertThrows(NoResultException.class, () -> orderDao.get(1));
        verify(query).setParameter(1, 1);
    }

    @Test
    public void getAll_happyFlow() {
        transportService = new NewTransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
        List<Order> ordersList =
                List.of(new Order("Testing BV", "Tes", "tes@mail.com", "Tessa 24 1000 Brussel", new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, transportService, Packaging.MEDIUM, new BigDecimal("20.10")),
                        new Order("Tim CO", "Tim", "tim@mail.com", "Timlaan 24 1000 Brussel", new Date(), List.of(new Product("Test product 3", new BigDecimal("7.40"))), Status.PROCESSED, transportService, Packaging.MEDIUM, new BigDecimal("7.40")),
                        new Order("Jan INC", "Jan", "jan@mail.com", "Janstraat 12 9000 Aalst", new Date(), List.of(new Product("Test product 4", new BigDecimal("1.20")), new Product("Test product 5", new BigDecimal("88.30"))), Status.POSTED, transportService, Packaging.CUSTOM, new BigDecimal("89.50"))
                );

        when(entityManager.createNamedQuery("Order.findAll", Order.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(ordersList);

        orderDao = new OrderJPADao(entityManager);

        assertEquals(ordersList, orderDao.getAll());
        verify(query).getResultList();
    }

    @Test
    public void processOrder_happyFlow() throws OrderStatusException {
        transportService = new NewTransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
        order = new Order("Testing BV", "Tes", "tes@mail.com", "Tessa 24 1000 Brussel", new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.POSTED, transportService, Packaging.MEDIUM, new BigDecimal("20.10"));


        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        mockFindAllPosted();

        orderDao = new OrderJPADao(entityManager);
        OrderController orderController = new OrderController(orderDao);

        orderController.processOrder(1, transportService);
        Order orderAfterUpdate = orderDao.get(1);

        assertEquals(transportService, orderAfterUpdate.getTransportService());
        assertEquals(Status.PROCESSED, orderAfterUpdate.getStatus());
//        verify(query, times(2)).setParameter(1, 1);
    }

    @Test
    public void processOrder_invalidBeginStatus_throwsOrderStatusException() {
        transportService = new NewTransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
        order = new Order("Testing BV", "Tes", "tes@mail.com", "Tessa 24 1000 Brussel", new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.OUT_FOR_DELIVERY, transportService, Packaging.MEDIUM, new BigDecimal("20.10"));

        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        mockFindAllPosted();

        orderDao = new OrderJPADao(entityManager);
        OrderController orderController = new OrderController(orderDao);

        assertThrows(OrderStatusException.class, () -> orderController.processOrder(1, transportService));
        verify(query).setParameter(1, 1);
    }

}
