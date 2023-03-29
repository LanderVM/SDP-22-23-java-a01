package domain;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carrier")
@NamedQueries({
        @NamedQuery(
                name = "Carrier.findById",
                query = "SELECT w FROM Carrier w WHERE w.carrierId = ?1"
        ),
        @NamedQuery(
                name = "Carrier.findAll",
                query = "SELECT d FROM Carrier d"
        ),
        @NamedQuery(
                name = "Carrier.findByName",
                query = "SELECT d FROM Carrier d WHERE d.name = ?1"
        ),
        @NamedQuery(
                name = "Carrier.findNameExists",
                query = "SELECT d FROM Carrier d WHERE d.name = ?1"
        ),
        @NamedQuery(
                name = "Carrier.findAllNames",
                query = "SELECT d.name FROM Carrier d"
        ),
        @NamedQuery(
                name = "Carrier.findAllNamesForSupplier",
                query = "SELECT d.name FROM Carrier d WHERE d.supplier.supplierId = ?1"
        ),
        @NamedQuery(
                name = "Carrier.findAllForSupplier",
                query = "SELECT d FROM Carrier d WHERE d.supplier.supplierId = ?1"
        ),
        @NamedQuery(
                name = "Carrier.findNameExistsForSupplier",
                query = "SELECT d FROM Carrier d WHERE d.supplier.supplierId = :supplierId  AND d.name = :name"
        ),
        @NamedQuery(
                name = "Carrier.findByNameForSupplier",
                query = "SELECT d FROM Carrier d WHERE d.supplier.supplierId = :supplierId AND d.name = :name"
        )
})
public class Carrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrier_id")
    private int carrierId;
    @Column(name = "name")
    private String name = "";
    @OneToMany
    private List<ContactPerson> contactPersonList;
    @OneToOne
    private TrackingCodeDetails trackingCodeDetails;
    @ManyToOne
    private Supplier supplier;
    @Column(name = "is_active")
    private boolean active = true;

    public Carrier(String name, List<ContactPerson> contactPersonList, TrackingCodeDetails trackingCodeDetails, Supplier supplier, boolean active) {
        this.name = name;
        this.setContactPersonList(contactPersonList);
        this.setTrackingCodeDetails(trackingCodeDetails);
        this.setSupplier(supplier);
        this.active = active;
    }

    protected Carrier() {
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public List<ContactPerson> getContactPersonList() {
        return contactPersonList;
    }

    void setContactPersonList(List<ContactPerson> contactPersonList) {
        if (contactPersonList == null)
            throw new IllegalArgumentException("contactPersonList may not be null!");
        this.contactPersonList = contactPersonList;
    }

    public TrackingCodeDetails getTrackingCodeDetails() {
        return trackingCodeDetails;
    }

    public void setTrackingCodeDetails(TrackingCodeDetails trackingCodeDetails) {
        if (trackingCodeDetails == null)
            throw new IllegalArgumentException("trackingCodeDetails may not be null!");
        this.trackingCodeDetails = trackingCodeDetails;
    }

    public boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        if (supplier == null)
            throw new IllegalArgumentException("supplier may not be null!");
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrier that = (Carrier) o;
        return carrierId == that.carrierId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrierId);
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "CarrierId=" + carrierId +
                ", name='" + name + '\'' +
                ", contactPersonList=" + contactPersonList +
                ", trackingCodeDetails=" + trackingCodeDetails +
                ", isActive=" + active +
                '}';
    }
}