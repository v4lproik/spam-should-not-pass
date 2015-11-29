package net.v4lproik.spamshouldnotpass.platform.models.dto;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContextDTO {

    private UUID id;

    private String name;

    private UUID userId;

    private DateTime date;

    private DateTime lastUpdate;

    private List<RuleDTO> rules = new ArrayList<>();

    public ContextDTO(UUID id, String name, UUID userId, DateTime date, DateTime lastUpdate, List<RuleDTO> rules) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.date = date;
        this.lastUpdate = lastUpdate;
        this.rules = rules;
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

    public List<RuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<RuleDTO> rules) {
        this.rules = rules;
    }
}
