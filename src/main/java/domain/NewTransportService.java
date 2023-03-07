package domain;


import jakarta.persistence.*;

import java.util.List;

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
}