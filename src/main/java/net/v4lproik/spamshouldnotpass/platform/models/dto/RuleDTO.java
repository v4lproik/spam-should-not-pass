package net.v4lproik.spamshouldnotpass.platform.models.dto;

import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RuleDTO {

    private UUID id;

    private String name;

    private String rule;

    private RuleType type;

    private UUID userId;

    private DateTime date;

    private DateTime lastUpdate;

    private List<Context> contexts = new ArrayList<>();

    public RuleDTO(UUID id, String name, String rule, RuleType type, UUID userId, DateTime date, DateTime lastUpdate, List<Context> contexts) {
        this.id = id;
        this.name = name;
        this.rule = rule;
        this.type = type;
        this.userId = userId;
        this.date = date;
        this.lastUpdate = lastUpdate;
        this.contexts = contexts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
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

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }
}
