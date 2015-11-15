package net.v4lproik.spamshouldnotpass.platform.models;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.UUID;

public final class BasicMember implements Serializable {

    UUID id;

    String email;

    String nickName;

    MemberStatus status;

    MemberPermission permission;

    public BasicMember(UUID id, String email, String nickName, MemberStatus memberStatus, MemberPermission memberPermission) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.status = memberStatus;
        this.permission = memberPermission;
    }

    public BasicMember(UUID id) {
        this.id = id;
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("nickName", nickName)
                .add("status", status)
                .add("permission", permission)
                .toString();
    }
}
