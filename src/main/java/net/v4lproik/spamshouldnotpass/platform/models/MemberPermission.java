package net.v4lproik.spamshouldnotpass.platform.models;

import com.google.common.base.Objects;

public enum MemberPermission {
    ADMIN(0, "ADMIN"),
    REGULAR(1, "REGULAR");

    private final int position;
    private final String letter;

    MemberPermission(int position, String letter) {
        this.position = position;
        this.letter = letter;
    }

    public static String get() {
        Objects.ToStringHelper toString = Objects.toStringHelper("");
        for (MemberPermission status : MemberPermission.values()) {
            toString.addValue(status.letter);
        }
        return toString.toString();
    }

    public int getPosition(){
        return this.position;
    }
}
