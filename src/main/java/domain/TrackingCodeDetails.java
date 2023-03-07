package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "trackingcodedetails")
public class TrackingCodeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_code_details_id")
    private int trackingCodeDetailsId;
    private int characterCount;
    private boolean integersOnly;
    private String prefix;
    private VerificationType verificationType;
}
