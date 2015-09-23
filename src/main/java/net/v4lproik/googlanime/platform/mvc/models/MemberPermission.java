package net.v4lproik.googlanime.platform.mvc.models;

import java.util.HashMap;
import java.util.Map;

public enum MemberPermission {
    ADMIN("A"),
    REGULAR("B");

    private static final Map<String, MemberPermission> lookup = new HashMap<String, MemberPermission>();
    static {
        for (MemberPermission d : MemberPermission.values()) {
            lookup.put(d.getMemberPermission(), d);
        }
    }

    private final String memberPermission;

    private MemberPermission(String memberPermission) {
        this.memberPermission = memberPermission;
    }

    public String getMemberPermission() {
        return memberPermission;
    }

    public static MemberPermission get(String grade) {
        return lookup.get(grade);
    }
}
