package net.v4lproik.spamshouldnotpass.platform.models.entities;

import com.google.common.base.Objects;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "`RulesInContext`",
            joinColumns = @JoinColumn(name = "`idRule`"), inverseJoinColumns = @JoinColumn(name = "`idContext`")
    )
    private List<Context> contexts = new ArrayList<>();

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

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
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
                .add("contexts", contexts)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule)) return false;
        Rule rule = (Rule) o;
        return Objects.equal(getId(), rule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
