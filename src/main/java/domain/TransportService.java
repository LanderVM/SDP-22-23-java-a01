package domain;

public enum TransportService {
    BPOST("BPost"),
    POSTNL("PostNL");

    final String name;

    TransportService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
