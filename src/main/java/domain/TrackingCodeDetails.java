package domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tracking_code_details")
public class TrackingCodeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_code_details_id")
    private int trackingCodeDetailsId;
    @Column(name = "character_count")
    private int characterCount = 0;
    @Column(name = "is_integers_only")
    private boolean integersOnly = false;
    @Column(name = "tracking_code_prefix")
    private String prefix = "";
    @Column(name = "verification_type")
    private VerificationType verificationType;

    public TrackingCodeDetails(int characterCount, boolean integersOnly, String prefix, VerificationType verificationType) {
        this.characterCount = characterCount;
        this.integersOnly = integersOnly;
        this.prefix = prefix;
        this.setVerificationType(verificationType);
    }

    protected TrackingCodeDetails() {
    }

    public int getCharacterCount() {
        return characterCount;
    }

    public void setCharacterCount(int characterCount) {
        if (this.characterCount <= 0)
            throw new IllegalArgumentException("Character count must be a positive number!");
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
    	if(verificationType==null)
    		throw new IllegalArgumentException("verificationType may not be null!");
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
