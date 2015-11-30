package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class toGetApiDTO {

    private String context;

    private List<APIInformationDTO> information;

    public toGetApiDTO() {
    }

    @JsonCreator
    public toGetApiDTO(@JsonProperty("context")String context, @JsonProperty("information")List<APIInformationDTO> information) {
        this.context = context;
        this.information = information;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<APIInformationDTO> getInformation() {
        return information;
    }

    public void setInformation(List<APIInformationDTO> information) {
        this.information = information;
    }
}
