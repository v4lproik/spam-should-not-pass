package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Objects;
import net.v4lproik.spamshouldnotpass.spring.deserializer.ListUUIDDeserializer;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class toUpdateContextDTO {

    @NotEmpty
    private UUID id;

    @NotEmpty
    private String name;

    @JsonDeserialize(using = ListUUIDDeserializer.class)
    @Nullable
    private List<UUID> rulesId;

    public toUpdateContextDTO(@JsonProperty("id")UUID id, @JsonProperty("name")String name, @JsonProperty("rulesId")List<UUID> rulesId) {
        this.id = id;
        this.name = name;
        this.rulesId = rulesId;
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

    @Nullable
    public List<UUID> getRulesId() {
        return rulesId;
    }

    public void setRulesId(@Nullable List<UUID> rulesId) {
        this.rulesId = rulesId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("rulesId", rulesId)
                .toString();
    }
}
