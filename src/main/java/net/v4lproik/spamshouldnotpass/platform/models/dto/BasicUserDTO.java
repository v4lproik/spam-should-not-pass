package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;

import java.util.UUID;

public class BasicUserDTO {

    UUID id;
    String email;
    String nickName;
    MemberStatus status;
    MemberPermission permission;

    @JsonCreator
    public BasicUserDTO(@JsonProperty("id") UUID id,
                        @JsonProperty("email")String email,
                        @JsonProperty("nickName")String nickName,
                        @JsonProperty("status")MemberStatus status,
                        @JsonProperty("permission")MemberPermission permission) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.status = status;
        this.permission = permission;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    public MemberPermission getPermission() {
        return permission;
    }

    public void setPermission(MemberPermission permission) {
        this.permission = permission;
    }
}
