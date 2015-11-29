package net.v4lproik.spamshouldnotpass.platform.models.dto;

import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import org.joda.time.DateTime;

import java.util.UUID;

public class SchemeDTO {

    private UUID id;

    private String properties;

    private UUID userId;

    private DateTime date;

    private DateTime lastUpdate;

    private SchemeType type;

    public SchemeDTO(UUID id, String properties, UUID userId, DateTime date, DateTime lastUpdate, SchemeType type) {
        this.id = id;
        this.properties = properties;
        this.userId = userId;
        this.date = date;
        this.lastUpdate = lastUpdate;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
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

    public SchemeType getType() {
        return type;
    }

    public void setType(SchemeType type) {
        this.type = type;
    }
}
