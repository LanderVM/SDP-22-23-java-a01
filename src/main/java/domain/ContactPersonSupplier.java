package domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "supplier_contact_person")
@NamedQueries({
        @NamedQuery(
                name = "ContactPersonSupplier.findAllForSupplier",
                query = "SELECT w FROM ContactPersonSupplier w WHERE w.supplier.email = ?1"
        )
})
public class ContactPersonSupplier {

    @Id
    @Column(name = "email_id")
    private String email = "";
    @Column(name = "full_name")
    private String name = "";
    @ManyToOne
    private Supplier supplier;

    public ContactPersonSupplier(String name, String email, Supplier supplier) {
        this.name = name;
        this.email = email;
        this.setSupplier(supplier);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        if (supplier == null)
            throw new IllegalArgumentException("supplier may not be null!");
        this.supplier = supplier;
    }

    protected ContactPersonSupplier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContactPersonSupplier other = (ContactPersonSupplier) obj;
        return Objects.equals(email, other.email);
    }

    @Override
    public String toString() {
        return "ContactPersonSupplier [name=" + name + ", email=" + email + ", supplier=" + supplier + "]";
    }
}
