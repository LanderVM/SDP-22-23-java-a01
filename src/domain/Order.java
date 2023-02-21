package domain;

import java.util.Date;
import java.util.List;

public record Order(String company, String customerName, int id, Date date, String address, double total,
                    List<Product> products, Status status, TransportService transportService, Packaging packaging) {
}