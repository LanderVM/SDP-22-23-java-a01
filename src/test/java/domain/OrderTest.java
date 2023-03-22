package domain;

import exceptions.OrderStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.impl.OrderDaoJpa;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderTest {


    @Mock
    OrderDaoJpa orderDao;
    @InjectMocks
    OrderController orderController;

    Supplier supplier;
    Supplier customer;
    

    Order order;
    TransportService transportService;

    @BeforeEach
    public void setup() {
        supplier = new Supplier("Tim CO", "tim@mail.com", "Timlaan 24 1000 Brussel", "0426343211", "/images/testImg.jpg");
        customer = new Supplier("Jan INC", "jan@mail.com", "Janstraat 12 9000 Aalst", "0456443212", "/images/testImg.jpg");
        transportService = new TransportService("test", List.of(), new TrackingCodeDetails(13, false, "testprefix", VerificationType.POST_CODE), true);
    }

    @Test
    public void processOrder_happyFlow() throws OrderStatusException {
        order = new Order(LocalDate.now(), "Stortlaan 76 Gent", List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.POSTED, transportService, PackagingType.MEDIUM, supplier, customer, new BigDecimal("7.70"));
        when(orderDao.get(1)).thenReturn(order);

        orderController.processOrder(1, transportService.getName());
        Order orderAfterUpdate = orderDao.get(1);

        assertEquals(transportService, orderAfterUpdate.getTransportService());
        assertEquals(Status.PROCESSED, orderAfterUpdate.getStatus());
        assertEquals(1, orderAfterUpdate.getNotifications().size());
        verify(orderDao, times(2)).get(1);
    }

    @Test
    public void processOrder_invalidBeginStatus_throwsOrderStatusException() {
        order = new Order(LocalDate.now(), "Stortlaan 76 Gent", List.of(new Product("Test product 1", new BigDecimal("10.30")), new Product("Test product 2", new BigDecimal("9.80"))), Status.DISPATCHED, null, PackagingType.MEDIUM, supplier, customer, new BigDecimal("7.70"));
        when(orderDao.get(1)).thenReturn(order);

        assertThrows(OrderStatusException.class, () -> orderController.processOrder(1,transportService.getName()));
        verify(orderDao).get(1);
    }
}