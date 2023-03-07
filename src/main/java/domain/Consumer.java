package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "consumer")
@NamedQueries({
        @NamedQuery(
                name = "Consumer.findByEmail",
                query = "SELECT w FROM Supplier w WHERE w.email = ?1"
        ),
        @NamedQuery(
                name = "Consumer.findById",
                query = "SELECT w FROM Supplier w WHERE w.customerId = ?1"
        ),
        @NamedQuery(
                name = "Consumer.findAll",
                query = "SELECT d FROM Supplier d"
        ),
})
public class Consumer extends Customer {

    public Consumer(String name, String email, String address, int phoneNumber, byte[] logo, List<Order> ordersList) {
        super(name, email, address, phoneNumber, logo, ordersList);
    }

    protected Consumer() {
        super();
    }
}
