package net.v4lproik.spamshouldnotpass.platform.service.api.entities;

import com.google.common.base.Objects;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="`Scheme`")
public class Scheme {

    @Id
    private UUID id;

    private String properties;

    @Column(name = "`userId`")
    private UUID userId;

    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @Column(name = "`lastUpdate`")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastUpdate;

    private SchemeType type;

    public Scheme() {
    }

    public Scheme(UUID id, String properties, UUID userId, DateTime date, DateTime lastUpdate, SchemeType type) {
        this.id = id;
        this.properties = properties;
        this.userId = userId;
        this.date = date;
        this.lastUpdate = lastUpdate;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(DateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public SchemeType getType() {
        return type;
    }

    public void setType(SchemeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("properties", properties)
                .add("userId", userId)
                .add("date", date)
                .add("lastUpdate", lastUpdate)
                .add("type", type)
                .toString();
    }
}
