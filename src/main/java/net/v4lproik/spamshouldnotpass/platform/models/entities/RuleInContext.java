package net.v4lproik.spamshouldnotpass.platform.models.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="`RulesInContext`")
public class RuleInContext {

    @EmbeddedId
    private CompositePKRulesInContext id;

    public RuleInContext() {
    }

    public RuleInContext(CompositePKRulesInContext id) {
        this.id = id;
    }
}
