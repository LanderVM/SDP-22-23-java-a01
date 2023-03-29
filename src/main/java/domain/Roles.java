package domain;

public enum Roles {
    ADMIN,
    STOREPERSON;

    @Override
    public String toString() {
        return name().substring(0, 0).toUpperCase() + name().substring(1);
    }

}
