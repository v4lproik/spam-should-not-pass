package net.v4lproik.googlanime.platform.models;

import java.util.HashMap;
import java.util.Map;

public enum MemberStatus {
    ADMIN("A"),
    USER("B");

    private static final Map<String, MemberStatus> lookup = new HashMap<String, MemberStatus>();
    static {
        for (MemberStatus d : MemberStatus.values()) {
            lookup.put(d.getMemberStatus(), d);
        }
    }

    private final String memberStatus;

    private MemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public static MemberStatus get(String grade) {
        return lookup.get(grade);
    }
}
