package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public class RulesInContextDTO {

    @NotEmpty
    private UUID idContext;

    @NotEmpty
    private List<toGetRuleDTO> listRules;

    @JsonCreator
    public RulesInContextDTO(@JsonProperty("idContext")UUID idContext, @JsonProperty("listRules")List<toGetRuleDTO> listRules) {
        this.idContext = idContext;
        this.listRules = listRules;
    }

    public UUID getIdContext() {
        return idContext;
    }

    public List<toGetRuleDTO> getListRules() {
        return listRules;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("idContext", idContext)
                .add("listRules", listRules)
                .toString();
    }
}
