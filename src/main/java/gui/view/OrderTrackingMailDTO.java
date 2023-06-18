package gui.view;

public record OrderTrackingMailDTO(String email, String trackingCode, String verificationCode) {

    @Override
    public String toString() {
        return "OrderTrackingMailDTO{" +
                "email='" + email + '\'' +
                ", trackingCode='" + trackingCode + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
