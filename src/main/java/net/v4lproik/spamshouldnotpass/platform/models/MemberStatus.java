package net.v4lproik.spamshouldnotpass.platform.models;

import com.google.common.base.Objects;

public enum MemberStatus {
    ADMIN(0, "ADMIN"),
    USER(1, "USER");

    private final int position;
    private final String letter;

    MemberStatus(int position, String letter) {
        this.position = position;
        this.letter = letter;
    }

    public static String get() {
        Objects.ToStringHelper toString = Objects.toStringHelper("");
        for (MemberStatus status : MemberStatus.values()) {
            toString.addValue(status.letter);
        }
        return toString.toString();
    }

    public int getPosition(){
        return this.position;
    }
}
