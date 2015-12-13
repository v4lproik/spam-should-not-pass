package net.v4lproik.spamshouldnotpass.platform.models;

public enum SchemeType {
    SPAMMER(0, "USER"),
    SPAM(1, "DOCUMENT");

    private final int position;
    private final String name;

    SchemeType(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public static SchemeType fromString(String value) {
        if (value != null) {
            for (SchemeType status : SchemeType.values()) {
                if (value.equalsIgnoreCase(status.name)) {
                    return status;
                }
            }
        }
        return null;
    }
}
