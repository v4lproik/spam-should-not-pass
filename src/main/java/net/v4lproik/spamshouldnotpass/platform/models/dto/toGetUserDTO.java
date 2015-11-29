package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

public class toGetUserDTO {

    @NotEmpty
    private UUID id;

    @JsonCreator
    public toGetUserDTO(@JsonProperty("id") UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
