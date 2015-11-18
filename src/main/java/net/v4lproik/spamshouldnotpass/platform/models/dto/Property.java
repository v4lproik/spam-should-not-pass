package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Property {

    private String variableType;

    private String variableName;

    public Property() {
    }

    public Property(@JsonProperty("variableType")String variableType, @JsonProperty("variableName")String variableName) {
        this.variableType = variableType;
        this.variableName = variableName;
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("variableType", variableType)
                .add("variableName", variableName)
                .toString();
    }
}

