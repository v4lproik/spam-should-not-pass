package net.v4lproik.spamshouldnotpass.platform.models;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.UUID;

public final class BasicMember implements Serializable {

    private final UUID id;
    private final String email;
    private final String nickName;
    private final MemberStatus status;
    private final MemberPermission permission;
    private final String corporation;

    public BasicMember(UUID id, String email, String nickName, MemberStatus memberStatus, MemberPermission memberPermission, String corporation) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.status = memberStatus;
        this.permission = memberPermission;
        this.corporation = corporation;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public MemberPermission getPermission() {
        return permission;
    }

    public String getCorporation() {
        return corporation;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("nickName", nickName)
                .add("status", status)
                .add("permission", permission)
                .add("corporation", corporation)
                .toString();
    }
}
