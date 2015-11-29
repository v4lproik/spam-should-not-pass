package net.v4lproik.spamshouldnotpass.platform.models.dto;

import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import org.joda.time.DateTime;

import java.util.UUID;

public class UserDTO {

    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private String nickname;

    private String password;

    private DateTime date;

    private String corporation;

    private MemberStatus status;

    private MemberPermission permission;

    public UserDTO(UUID id, String firstname, String lastname, String email, String nickname, String password, DateTime date, String corporation, MemberStatus status, MemberPermission permission) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.date = date;
        this.corporation = corporation;
        this.status = status;
        this.permission = permission;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
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
