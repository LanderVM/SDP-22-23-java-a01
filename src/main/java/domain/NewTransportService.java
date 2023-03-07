package domain;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "transport_service")
public class NewTransportService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_service_id")
    private int transportServiceId;
    @OneToMany
    private List<ContactPerson> contactPersonList;
    private int trackingCodeCharacterCount;
    @OneToOne
    private TrackingCodeDetails trackingCodeDetails;
    private boolean isActive;

    public int getTransportServiceId() {
        return transportServiceId;
    }

    public void setTransportServiceId(int transportServiceId) {
        this.transportServiceId = transportServiceId;
    }

    public List<ContactPerson> getContactPersonList() {
        return contactPersonList;
    }

    public void setContactPersonList(List<ContactPerson> contactPersonList) {
        this.contactPersonList = contactPersonList;
    }

    public int getTrackingCodeCharacterCount() {
        return trackingCodeCharacterCount;
    }

    public void setTrackingCodeCharacterCount(int trackingCodeCharacterCount) {
        this.trackingCodeCharacterCount = trackingCodeCharacterCount;
    }

    public TrackingCodeDetails getTrackingCodeDetails() {
        return trackingCodeDetails;
    }

    public void setTrackingCodeDetails(TrackingCodeDetails trackingCodeDetails) {
        this.trackingCodeDetails = trackingCodeDetails;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTransportService that = (NewTransportService) o;
        return transportServiceId == that.transportServiceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportServiceId);
    }

    @Override
    public String toString() {
        return "NewTransportService{" +
                "transportServiceId=" + transportServiceId +
                ", contactPersonList=" + contactPersonList +
                ", trackingCodeCharacterCount=" + trackingCodeCharacterCount +
                ", trackingCodeDetails=" + trackingCodeDetails +
                ", isActive=" + isActive +
                '}';
    }
}