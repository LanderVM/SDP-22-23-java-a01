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
import persistence.UserJPADao;

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
    private UserJPADao userJPADao;

    private Order order;
    private TransportService transportService;

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
    
    Supplier s1 = new Supplier("Tim CO","tim@mail.com","Timlaan 24 1000 Brussel" , "0426343211", "/images/testImg.jpg");
    Supplier s2 = new Supplier("Jan INC","jan@mail.com","Janstraat 12 9000 Aalst", "0456443212", "/images/testImg.jpg");
    
    @Test
    public void getById_happyFlow() {
        transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);

        order = new Order( new Date(), List.of(new Product("Test product 1",new BigDecimal("11.70") ),new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, transportService, Packaging.MEDIUM, s1, s2, new BigDecimal("10.30"));
        
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
        transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
        List<Order> ordersList =
                
                List.of(new Order( new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, transportService, Packaging.LARGE, s1, s2,new BigDecimal("8.30")),
                		new Order( new Date(), List.of(new Product("Test product 3", new BigDecimal("11.50")), new Product("Test product 1", new BigDecimal("9.80"))), Status.DISPATCHED, transportService, Packaging.MEDIUM, s2, s1,new BigDecimal("5.50")),
                		new Order( new Date(), List.of(new Product("Test product 2", new BigDecimal("9.80")), new Product("Test product 4", new BigDecimal("11.50"))), Status.POSTED, transportService, Packaging.MEDIUM, s1, s2,new BigDecimal("6.90"))
                );

        when(entityManager.createNamedQuery("Order.findAll", Order.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(ordersList);

        orderDao = new OrderJPADao(entityManager);

        assertEquals(ordersList, orderDao.getAll());
        verify(query).getResultList();
    }

    @Test
    public void processOrder_happyFlow() throws OrderStatusException {
        transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);

        order = new Order( new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.POSTED, transportService, Packaging.MEDIUM, s1, s2,new BigDecimal("7.70"));

        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        mockFindAllPosted();

        orderDao = new OrderJPADao(entityManager);
        userJPADao = new UserJPADao(entityManager);
        OrderController orderController = new OrderController(orderDao, userJPADao);

        orderController.processOrder(1, transportService);
        Order orderAfterUpdate = orderDao.get(1);

        assertEquals(transportService, orderAfterUpdate.getTransportService());
        assertEquals(Status.PROCESSED, orderAfterUpdate.getStatus());
//        verify(query, times(2)).setParameter(1, 1);
    }

    @Test
    public void processOrder_invalidBeginStatus_throwsOrderStatusException() {
        transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
        
        order = new Order( new Date(), List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, transportService, Packaging.MEDIUM, s1, s2,new BigDecimal("12.40"));

        when(entityManager.createNamedQuery("Order.findById", Order.class)).thenReturn(query);
        when(query.setParameter(1, 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        mockFindAllPosted();

        orderDao = new OrderJPADao(entityManager);
        userJPADao = new UserJPADao(entityManager);
        OrderController orderController = new OrderController(orderDao, userJPADao);

        assertThrows(OrderStatusException.class, () -> orderController.processOrder(1, transportService));
        verify(query).setParameter(1, 1);
    }

}
