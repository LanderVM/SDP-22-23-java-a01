package domain;

import java.util.Arrays;

public enum PackagingType {
    STANDARD,
    CUSTOM;

    static boolean exists(String packagingType) {
        return Arrays.stream(PackagingType.values()).map(Enum::toString).toList().contains(packagingType);
    }
}
