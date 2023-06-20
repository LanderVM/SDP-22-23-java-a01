package domain;

public enum Status {
    POSTED, PROCESSED, DISPATCHED, OUT_FOR_DELIVERY, DELIVERED;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase().replaceAll("_", " ");
    }

}
