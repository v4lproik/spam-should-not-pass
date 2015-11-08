package net.v4lproik.spamshouldnotpass.platform.models;

import com.google.common.base.Objects;

public enum MemberStatus {
    ADMIN(0, "ADMIN"),
    USER(1, "USER");

    private final int position;
    private final String name;

    MemberStatus(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public static String get() {
        Objects.ToStringHelper toString = Objects.toStringHelper("");
        for (MemberStatus status : MemberStatus.values()) {
            toString.addValue(status.name);
        }
        return toString.toString();
    }

    public static MemberStatus fromString(String value) {
        if (value != null) {
            for (MemberStatus status : MemberStatus.values()) {
                if (value.equalsIgnoreCase(status.name)) {
                    return status;
                }
            }
        }
        return null;
    }

    public int getPosition(){
        return this.position;
    }
}
