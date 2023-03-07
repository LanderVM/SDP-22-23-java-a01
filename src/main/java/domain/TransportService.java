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
                name = "TransportService.findAllActive",
                query = "SELECT d FROM TransportService d WHERE d.isActive = true"
        )
})
public class TransportService {

    // TODO: validation for TransportController addTransportService, viewTransportService, deactivateTransportService, activateTransportService

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_service_id")
    private int transportServiceId;
    private String name;
    @OneToMany
    private List<ContactPerson> contactPersonList;
    @OneToOne
    private TrackingCodeDetails trackingCodeDetails;
    private boolean isActive;

    public TransportService(String name, List<ContactPerson> contactPersonList, TrackingCodeDetails trackingCodeDetails, boolean isActive) {
        this.name = name;
        this.contactPersonList = contactPersonList;
        this.trackingCodeDetails = trackingCodeDetails;
        this.isActive = isActive;
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

    void setTrackingCodeDetails(TrackingCodeDetails trackingCodeDetails) {
        this.trackingCodeDetails = trackingCodeDetails;
    }

    public boolean isActive() {
        return isActive;
    }

    void setActive(boolean active) {
        isActive = active;
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
                ", isActive=" + isActive +
                '}';
    }
}