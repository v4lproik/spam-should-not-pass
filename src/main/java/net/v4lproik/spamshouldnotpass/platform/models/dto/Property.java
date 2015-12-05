package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.google.common.base.Objects;

public class Property {

    private String variableType;

    private String variableName;

    private Integer position;

    private Boolean visibility;

    public Property() {
    }

    public Property(String variableType, String variableName) {
        this.variableType = variableType;
        this.variableName = variableName;
    }

    public Property(String variableType, String variableName, Integer position, Boolean visibility) {
        this.variableType = variableType;
        this.variableName = variableName;
        this.position = position;
        this.visibility = visibility;
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

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("variableType", variableType)
                .add("variableName", variableName)
                .add("position", position)
                .add("visibility", visibility)
                .toString();
    }
}

