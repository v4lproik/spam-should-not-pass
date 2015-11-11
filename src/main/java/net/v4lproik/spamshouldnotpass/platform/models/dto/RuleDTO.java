package net.v4lproik.spamshouldnotpass.platform.models.dto;

import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import org.hibernate.validator.constraints.NotEmpty;

public class RuleDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String rule;

    @NotEmpty
    private RuleType type;

    public RuleDTO(String name, String rule, RuleType type) {
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
}

