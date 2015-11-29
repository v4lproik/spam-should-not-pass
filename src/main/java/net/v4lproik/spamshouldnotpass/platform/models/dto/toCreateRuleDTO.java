package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import java.util.UUID;

public class toCreateRuleDTO {

    @Nullable
    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String rule;

    @NotEmpty
    private RuleType type;

    public toCreateRuleDTO(@JsonProperty("name") String name, @JsonProperty("rule") String rule, @JsonProperty("type") RuleType type) {
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

    @Nullable
    public UUID getId() {
        return id;
    }
}

