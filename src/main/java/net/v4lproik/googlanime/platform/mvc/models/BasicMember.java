package net.v4lproik.googlanime.platform.mvc.models;

import java.io.Serializable;

public final class BasicMember implements Serializable {

    Long id;

    String email;

    String nickName;

    MemberStatus status;

    MemberPermission permission;

    public BasicMember(Long id, String email, String nickName, MemberStatus memberStatus, MemberPermission memberPermission) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.status = memberStatus;
        this.permission = memberPermission;
    }

    public BasicMember(Long id) {
        this.id = id;
    }

    public Long getId() {
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

}
