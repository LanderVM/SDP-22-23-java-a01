package domain;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "transport_service")
@NamedQueries({
        @NamedQuery(
                name = "TransportService.findById",
                query = "SELECT w FROM TransportService w WHERE w.transportServiceId = ?1"
        ),
        @NamedQuery(
                name = "TransportService.findAll",
                query = "SELECT d FROM TransportService d"
        ),
        @NamedQuery(
                name = "TransportService.findByName",
                query = "SELECT d FROM TransportService d WHERE d.name = ?1"
        ),
        @NamedQuery(
                name = "TransportService.findNameExists",
                query = "SELECT d FROM TransportService d WHERE d.name = ?1"
        ),
        @NamedQuery(
        		name = "TransportService.findAllNames",
                query = "SELECT d.name FROM TransportService d"
        ),
        @NamedQuery(
        		name = "TransportService.findAllNamesForSupplier",
                query = "SELECT d.name FROM TransportService d WHERE d.supplier.supplierId = ?1"
        ),
        @NamedQuery(
        		name = "TransportService.findAllForSupplier",
                query = "SELECT d FROM TransportService d WHERE d.supplier.supplierId = ?1"
        ),
        @NamedQuery(
        		name = "TransportService.findNameExistsForSupplier", 
        		query = "SELECT d FROM TransportService d WHERE d.supplier.supplierId = :supplierId  AND d.name = :name"
        ),
        @NamedQuery(
                name = "TransportService.findByNameForSupplier",
                query = "SELECT d FROM TransportService d WHERE d.supplier.supplierId = :supplierId AND d.name = :name"
        )
})
public class TransportService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_service_id")
    private int transportServiceId;
    private String name;
    @OneToMany
    private List<ContactPerson> contactPersonList;
    @OneToOne
    private TrackingCodeDetails trackingCodeDetails;
    
    @ManyToOne
    private Supplier supplier;
    
    private boolean active;

    public TransportService(String name, List<ContactPerson> contactPersonList, TrackingCodeDetails trackingCodeDetails,Supplier supplier ,boolean active) {
        this.name = name;
        this.contactPersonList = contactPersonList;
        this.trackingCodeDetails = trackingCodeDetails;
        this.supplier = supplier;
        this.active = active;
    }

    protected TransportService() {
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getTransportServiceId() {
        return transportServiceId;
    }

    public List<ContactPerson> getContactPersonList() {
        return contactPersonList;
    }

    void setContactPersonList(List<ContactPerson> contactPersonList) {
        this.contactPersonList = contactPersonList;
    }

    public TrackingCodeDetails getTrackingCodeDetails() {
        return trackingCodeDetails;
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
		this.supplier = supplier;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportService that = (TransportService) o;
        return transportServiceId == that.transportServiceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportServiceId);
    }

    @Override
    public String toString() {
        return "TransportService{" +
                "transportServiceId=" + transportServiceId +
                ", name='" + name + '\'' +
                ", contactPersonList=" + contactPersonList +
                ", trackingCodeDetails=" + trackingCodeDetails +
                ", isActive=" + active +
                '}';
    }
}