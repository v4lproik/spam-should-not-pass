package net.v4lproik.spamshouldnotpass.platform.models.entities;

import com.google.common.base.Objects;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="`User`")
public class User {

    @Id
    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private String nickname;

    private String password;

    private MemberStatus status;

    private MemberPermission permission;

    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    public User() {
    }

    public User(UUID id) {
        this.id = id;
    }

    public User(UUID id, String firstname, String lastname, String email, String nickname,
                String password, MemberStatus status, MemberPermission permission, DateTime date) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
        this.permission = permission;
        this.date = date;
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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equal(getId(), user.getId()) &&
                Objects.equal(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getEmail());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("email", email)
                .add("nickname", nickname)
                .add("password", password)
                .add("status", status)
                .add("permission", permission)
                .add("date", date)
                .toString();
    }
}
