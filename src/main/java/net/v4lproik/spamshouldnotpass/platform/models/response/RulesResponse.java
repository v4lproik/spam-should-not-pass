package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.dto.RuleDTO;

import java.util.List;

public class RulesResponse extends PlatformResponse{

    private List<RuleDTO> rules;

    public RulesResponse(List<RuleDTO> rules) {
        super();
        this.rules = rules;
    }

    public RulesResponse(Status status, Error error, String message) {
        super(status, error, message);
        this.rules = null;
    }

    public List<RuleDTO> getRules() {
        return rules;
    }
}
