package domain;

public enum Rolles {
    ADMIN,
    MAGAZIJNIER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
