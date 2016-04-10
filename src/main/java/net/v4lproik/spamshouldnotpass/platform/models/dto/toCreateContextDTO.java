package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class toCreateContextDTO {

    @Nullable
    private UUID id;

    @NotEmpty
    private String name;

    @Nullable
    private UUID userId;

    private DateTime date;

    private DateTime lastUpdate;

    private List<Rule> rules = new ArrayList<>();

    public toCreateContextDTO(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public UUID getId() {
        return id;
    }
}

