package domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "supplier")
@NamedQueries({
        @NamedQuery(
                name = "Supplier.findByEmail",
                query = "SELECT w FROM Supplier w WHERE w.email = ?1"
        ),
        @NamedQuery(
                name = "Supplier.findById",
                query = "SELECT w FROM Supplier w WHERE w.customerId = ?1"
        ),
        @NamedQuery(
                name = "Supplier.findAll",
                query = "SELECT d FROM Supplier d"
        ),
        @NamedQuery(
                name = "Supplier.findAllWithOrdersAsCustomer",
                query = "SELECT d FROM Supplier d WHERE d.ordersList IS NOT EMPTY"
        ),
})
public class Supplier extends Customer {

    public Supplier(String name, String email, String address, int phoneNumber, byte[] logo, List<Order> ordersList) {
        super(name, email, address, phoneNumber, logo, ordersList);
    }

    protected Supplier() {
        super();
    }
}
