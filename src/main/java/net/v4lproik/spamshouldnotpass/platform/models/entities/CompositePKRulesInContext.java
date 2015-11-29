package net.v4lproik.spamshouldnotpass.platform.models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class CompositePKRulesInContext implements Serializable{

    @Column(name = "`idRule`")
    private UUID idRule;

    @Column(name = "`idContext`")
    private UUID idContext;

    public CompositePKRulesInContext(UUID idRule, UUID idContext) {
        this.idRule = idRule;
        this.idContext = idContext;
    }


    public UUID getIdRule() {
        return idRule;
    }

    public UUID getIdContext() {
        return idContext;
    }
}
