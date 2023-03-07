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

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
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

    private void mockFindAllPosted() {
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);
    
    }
    
    private byte[] getFile() {
    	File fi = new File("testImg.jpg");
        byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(fi.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileContent;
    }
    
    Supplier s1 = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" ,0426343211, getFile());
    Supplier s2 = new Supplier("Jan INC","jan@mail.com","Janstraat 12 9000 Aalst",0456443212, getFile());
    
    @Test
    public void getById_happyFlow() {
        order = new Order( new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM, s1, s2);
        
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
        List<Order> ordersList =
                List.of(new Order( new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, TransportService.POSTNL, Packaging.LARGE, s1, s2),
                		new Order( new Date(), List.of(new Product("Test product 3", new BigDecimal("11.50")), new Product("Test product 1", new BigDecimal("9.80"))), Status.DISPATCHED, TransportService.BPOST, Packaging.MEDIUM, s2, s1),
                		new Order( new Date(), List.of(new Product("Test product 2", new BigDecimal("9.80")), new Product("Test product 4", new BigDecimal("11.50"))), Status.POSTED, TransportService.POSTNL, Packaging.MEDIUM, s1, s2)
                );

        when(entityManager.createNamedQuery("Order.findAll", Order.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(ordersList);

        orderDao = new OrderJPADao(entityManager);

        assertEquals(ordersList, orderDao.getAll());
        verify(query).getResultList();
    }

    @Test
    public void processOrder_happyFlow() throws OrderStatusException {
        order = new Order( new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.POSTED, TransportService.POSTNL, Packaging.MEDIUM, s1, s2);

        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        mockFindAllPosted();

        orderDao = new OrderJPADao(entityManager);
        OrderController orderController = new OrderController(orderDao);

        orderController.processOrder(1, TransportService.BPOST);
        Order orderAfterUpdate = orderDao.get(1);

        assertEquals(TransportService.BPOST, orderAfterUpdate.getTransportService());
        assertEquals(Status.PROCESSED, orderAfterUpdate.getStatus());
//        verify(query, times(2)).setParameter(1, 1);
    }

    @Test
    public void processOrder_invalidBeginStatus_throwsOrderStatusException() {
        order = new Order( new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, TransportService.POSTNL, Packaging.MEDIUM, s1, s2);

        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        mockFindAllPosted();

        orderDao = new OrderJPADao(entityManager);
        OrderController orderController = new OrderController(orderDao);

        assertThrows(OrderStatusException.class, () -> orderController.processOrder(1, TransportService.BPOST));
        verify(query).setParameter(1, 1);
    }

}
