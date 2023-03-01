package domain;

public enum Packaging {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large"),
    CUSTOM("Custom");

    final String name;

    Packaging(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
