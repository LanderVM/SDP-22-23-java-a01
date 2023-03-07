package domain;

import jakarta.persistence.*;

import java.util.Objects;

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

    public TrackingCodeDetails(int characterCount, boolean integersOnly, String prefix, VerificationType verificationType) {
        this.characterCount = characterCount;
        this.integersOnly = integersOnly;
        this.prefix = prefix;
        this.verificationType = verificationType;
    }

    protected TrackingCodeDetails() {
    }

    public int getTrackingCodeDetailsId() {
        return trackingCodeDetailsId;
    }

    public int getCharacterCount() {
        return characterCount;
    }

    public void setCharacterCount(int characterCount) {
        this.characterCount = characterCount;
    }

    public boolean isIntegersOnly() {
        return integersOnly;
    }

    public void setIntegersOnly(boolean integersOnly) {
        this.integersOnly = integersOnly;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public VerificationType getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(VerificationType verificationType) {
        this.verificationType = verificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingCodeDetails that = (TrackingCodeDetails) o;
        return trackingCodeDetailsId == that.trackingCodeDetailsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingCodeDetailsId);
    }

    @Override
    public String toString() {
        return "TrackingCodeDetails{" +
                "trackingCodeDetailsId=" + trackingCodeDetailsId +
                ", characterCount=" + characterCount +
                ", integersOnly=" + integersOnly +
                ", prefix='" + prefix + '\'' +
                ", verificationType=" + verificationType +
                '}';
    }
}
