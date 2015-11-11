package net.v4lproik.spamshouldnotpass.platform.models.entities;

import com.google.common.base.Objects;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="`Rule`")
public class Rule {

    @Id
    private UUID id;

    private String name;

    private String rule;

    @Enumerated(EnumType.ORDINAL)
    private RuleType type;

    @Column(name = "`userId`")
    private UUID userId;

    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @Column(name = "`lastUpdate`")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastUpdate;

    public Rule() {
    }

    public Rule(UUID id, String name, String rule, RuleType type, UUID userId, DateTime date, DateTime lastUpdate) {
        this.id = id;
        this.name = name;
        this.rule = rule;
        this.type = type;
        this.userId = userId;
        this.date = date;
        this.lastUpdate = lastUpdate;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRule() {
        return rule;
    }

    public RuleType getType() {
        return type;
    }

    public UUID getUserId() {
        return userId;
    }

    public DateTime getDate() {
        return date;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setLastUpdate(DateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("rule", rule)
                .add("type", type)
                .add("userId", userId)
                .add("date", date)
                .add("lastUpdate", lastUpdate)
                .toString();
    }
}
