package net.v4lproik.spamshouldnotpass.platform.models.entities;

import com.google.common.base.Objects;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="`Context`")
public class Context {

    @Id
    private UUID id;

    private String name;

    @Column(name = "`userId`")
    private UUID userId;

    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @Column(name = "`lastUpdate`")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastUpdate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "`RulesInContext`", joinColumns = @JoinColumn(name = "`idContext`"), inverseJoinColumns = @JoinColumn(name = "`idRule`"))
    private List<Rule> rules = new ArrayList<>();

    public Context() {
    }

    public Context(UUID id, String name, UUID userId, DateTime date, DateTime lastUpdate) {
        this.id = id;
        this.name = name;
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

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setLastUpdate(DateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("userId", userId)
                .add("date", date)
                .add("lastUpdate", lastUpdate)
                .add("rules", rules)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Context)) return false;
        Context context = (Context) o;
        return Objects.equal(getId(), context.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
