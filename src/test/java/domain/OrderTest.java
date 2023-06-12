package domain;

import exceptions.EntityDoesntExistException;
import exceptions.OrderStatusException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.impl.OrderDaoJpa;
import persistence.impl.CarrierDaoJpa;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderTest {


    @Mock
    OrderDaoJpa orderDao;
    @Mock
    CarrierDaoJpa carrierDao;
    @InjectMocks
    OrderController orderController;

    Order order;
    Supplier supplier;
    Supplier customer;
    Carrier carrier;

    @BeforeEach
    public void setup() {
        supplier = new Supplier("Tim CO", "tim@mail.com", "Belgie", "Brussel", "1000", "Zandstraat", 20, "A", "0426343211", "/images/testImg.jpg");
        customer = new Supplier("Jan INC", "jan@mail.com", "Belgie", "Brussel", "1210", "Koningsstraat", 236, "B", "0456443212", "/images/testImg.jpg");
        carrier = new Carrier("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", "POST_CODE"),supplier, true);
    }
    
    @Test
    public void processOrder_happyFlow() throws OrderStatusException, EntityDoesntExistException  {
    	order = new Order(LocalDate.now(), "Belgie", "Lokeren", "9160", "Honkstraat", 33, "", List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.POSTED, carrier, new Packaging("Packaging", 2, 3, 4, 15, PackagingType.STANDARD, true, supplier), supplier, customer, new BigDecimal("7.70"));
        when(orderDao.get(0)).thenReturn(order);
        when(carrierDao.getForSupplier("test", 0)).thenReturn(carrier);
        when(orderDao.getAllForUser(0)).thenReturn(List.of(order));
        
        orderController.getOrderList(0, false);
        
		orderController.processOrder(0, carrier.getName(),0);
		
        Order orderAfterUpdate = orderDao.get(0);
        
        assertEquals(carrier, orderAfterUpdate.getCarrier());
        assertEquals(Status.PROCESSED, orderAfterUpdate.getStatus());
        assertEquals(1, orderAfterUpdate.getNotifications().size());
        verify(orderDao, times(2)).get(0);
    }
    
    @Test
    public void processOrder_orderDoesntExist_throwsEntityDoesntExistException () {
    	order = new Order(LocalDate.now(), "Belgie", "Lokeren", "9160", "Honkstraat", 33, "", List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, null, new Packaging("Packaging", 2, 3, 4, 15, PackagingType.STANDARD, true, supplier), supplier, customer, new BigDecimal("7.70"));
        when(orderDao.get(1)).thenReturn(null);
        
        assertThrows(EntityDoesntExistException.class, () -> orderController.processOrder(1, carrier.getName(),0));
        verify(orderDao).get(1);
    }
    
    @Test
    public void processOrder_transportServiceDoesntExist_throwsEntityDoesntExistException () {
    	order = new Order(LocalDate.now(), "Belgie", "Lokeren", "9160", "Honkstraat", 33, "", List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, null, new Packaging("Packaging", 2, 3, 4, 15, PackagingType.STANDARD, true, supplier), supplier, customer, new BigDecimal("7.70"));
        when(orderDao.get(1)).thenReturn(order);
        when(carrierDao.getForSupplier("test", 0)).thenReturn(null);
        assertThrows(EntityDoesntExistException.class, () -> orderController.processOrder(1, carrier.getName(),0));
        verify(orderDao).get(1);
        verify(carrierDao).getForSupplier("test",0);
    }

    @Test
    public void processOrder_invalidBeginStatus_throwsOrderStatusException() {
        order = new Order(LocalDate.now(), "Belgie", "Lokeren", "9160", "Honkstraat", 33, "", List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, null, new Packaging("Packaging", 2, 3, 4, 15, PackagingType.STANDARD, true, supplier), supplier, customer, new BigDecimal("7.70"));
        when(orderDao.get(1)).thenReturn(order);
        when(carrierDao.getForSupplier("test", 0)).thenReturn(carrier);

        assertThrows(OrderStatusException.class, () -> orderController.processOrder(1, carrier.getName(),0));
        verify(orderDao).get(1);
        verify(carrierDao).getForSupplier("test",0);
    }
    
    @Test
    public void testGenerateTrackingCode() {
    	order = new Order(LocalDate.now(), "Belgie", "Lokeren", "9160", "Honkstraat", 33, "", List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, new Carrier("bpost", List.of(new ContactPerson("contact1", "contact1@gmail.com")), new TrackingCodeDetails(10, true, "32", "POST_CODE"),supplier, true), new Packaging("Packaging", 2, 3, 4, 15, PackagingType.STANDARD, true, supplier), supplier, customer, new BigDecimal("7.70"));
        
        order.generateTrackingCode();
        String trackingCode = order.getTrackingCode();

        assertEquals(order.getCarrier().getTrackingCodeDetails().getCharacterCount() + order.getCarrier().getTrackingCodeDetails().getPrefix().length(), trackingCode.length());
       
        assertTrue(trackingCode.startsWith(order.getCarrier().getTrackingCodeDetails().getPrefix()));

        assertTrue(trackingCode.matches("[0-9A-Z]+"));
       
    }
    
    @Test
    public void addNotificationToOrder_happyFlow() {
        Order order = new Order();
        Notification notification = new Notification();
        
        notification.setOrder(order);
        order.addNotification(notification);

        assertTrue(order.getNotifications().contains(notification));

    }
    
    @Test
    public void addNotificationToOrder_throwsException() {
    	Order order = new Order();
        Notification notification = new Notification();
        Notification InvalidNotification = new Notification();
        
        notification.setOrder(order);
        order.addNotification(notification);
        
        assertThrows(RuntimeException.class, () -> order.addNotification(InvalidNotification));
    }
}