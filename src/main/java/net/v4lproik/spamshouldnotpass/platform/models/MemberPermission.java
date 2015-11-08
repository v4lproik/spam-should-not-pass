package net.v4lproik.spamshouldnotpass.platform.models;

import com.google.common.base.Objects;

public enum MemberPermission {
    ADMIN(0, "ADMIN"),
    REGULAR(1, "REGULAR");

    private final int position;
    private final String name;

    MemberPermission(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public static String get() {
        Objects.ToStringHelper toString = Objects.toStringHelper("");
        for (MemberPermission status : MemberPermission.values()) {
            toString.addValue(status.name);
        }
        return toString.toString();
    }

    public static MemberPermission fromString(String value) {
        if (value != null) {
            for (MemberPermission status : MemberPermission.values()) {
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
