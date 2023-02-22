package test;


import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;



public class OrderTest {
    private String company;
    private String customerName;
    private int id;
    private Date date;
    private String address;
    private double total;
    private List<Product> products;
    private Status status;
    private TransportService transportService;
    private Packaging packaging;

    @BeforeEach
    public void setUp() {
        company = "Delaware";
        customerName = "John Smith";
        id = 123;
        date = new Date();
        address = "123 TestStreet Anytown BE";
        total = 100.0;
        products = new ArrayList<Product>();
        products.add(new Product("Product 1",1, 10.0, 25.00));
        products.add(new Product("Product 2",1, 10.0, 25.00));
        status = Status.PROCESSED;
        transportService = TransportService.TEMPORARY_ONE;
        packaging = Packaging.TEMPORARY_TWO;
    }

    @Test
    public void testOrderCreation() {
        Order order = new Order(company, customerName, id, date, address, total, products, status, transportService, packaging);
        assertNotNull(order);
        assertEquals(company, order.company());
        assertEquals(customerName, order.customerName());
        assertEquals(id, order.id());
        assertEquals(date, order.date());
        assertEquals(address, order.address());
        assertEquals(total, order.total());
        assertEquals(products, order.products());
        assertEquals(status, order.status());
        assertEquals(transportService, order.transportService());
        assertEquals(packaging, order.packaging());
    }

    @Test
    public void testOrderCreationWithNullCompany() {
        Order order = new Order(null, customerName, id, date, address, total, products, status, transportService, packaging);
        assertNotNull(order);
        assertNull(order.company());
        assertEquals(customerName, order.customerName());
        assertEquals(id, order.id());
        assertEquals(date, order.date());
        assertEquals(address, order.address());
        assertEquals(total, order.total());
        assertEquals(products, order.products());
        assertEquals(status, order.status());
        assertEquals(transportService, order.transportService());
        assertEquals(packaging, order.packaging());
    }
    }