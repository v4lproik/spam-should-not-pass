package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class toCreateUserDTO {

    @NotEmpty
    @Size(min = 2, max = 30)
    private String firstname;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String lastname;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private MemberStatus status;

    @NotEmpty
    private MemberPermission permission;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String corporation;

    @JsonCreator
    public toCreateUserDTO(@JsonProperty("fistname") String firstname,
                           @JsonProperty("lastname") String lastname,
                           @JsonProperty("email") String email,
                           @JsonProperty("password") String password,
                           @JsonProperty("status") String status,
                           @JsonProperty("permission") String permission,
                           @JsonProperty("corporation") String corporation
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.status = MemberStatus.fromString(status);
        this.permission = MemberPermission.fromString(permission);
        this.corporation = corporation;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
}
