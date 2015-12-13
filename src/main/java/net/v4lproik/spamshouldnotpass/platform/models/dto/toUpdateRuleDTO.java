package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

public class toUpdateRuleDTO {

    @NotEmpty
    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String rule;

    @NotEmpty
    private RuleType type;

    public toUpdateRuleDTO(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("rule") String rule, @JsonProperty("type") RuleType type) {
        this.id = id;
        this.name = name;
        this.rule = rule;
        this.type = type;
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

    public UUID getId() {
        return id;
    }
}

