package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.google.common.base.Objects;

import java.util.List;

public class Properties {
    private List<Property> properties;

    public Properties() {
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("properties", properties)
                .toString();
    }
}