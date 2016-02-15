package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
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

//    public toCreateRuleDTO(@JsonProperty("name") String name, @JsonProperty("rule") String rule, @JsonProperty("type") RuleType type) {
//        this.name = name;
//        this.rule = rule;
//        this.type = type;
//    }

    public toCreateRuleDTO(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("rule") String rule, @JsonProperty("type") RuleType type) {
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

    @Nullable
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("rule", rule)
                .add("type", type)
                .toString();
    }
}

