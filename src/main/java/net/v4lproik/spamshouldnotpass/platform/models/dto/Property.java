package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.google.common.base.Objects;

public class Property {

    private String variableType;

    private String variableName;

    private Integer position;

    private Boolean locked;

    private Boolean provided;

    public Property() {
    }

    public Property(String variableType, String variableName, Integer position, Boolean locked, Boolean provided) {
        this.variableType = variableType;
        this.variableName = variableName;
        this.position = position;
        this.locked = locked;
        this.provided = provided;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean isProvided() {
        return provided;
    }

    public void setProvided(Boolean provided) {
        this.provided = provided;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("variableType", variableType)
                .add("variableName", variableName)
                .add("position", position)
                .add("locked", locked)
                .add("provided", provided)
                .toString();
    }
}

