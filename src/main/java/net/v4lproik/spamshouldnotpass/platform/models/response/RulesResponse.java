package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;

import java.util.List;

public class RulesResponse extends PlatformResponse{

    private List<Rule> rules;

    public RulesResponse(List<Rule> rules) {
        super();
        this.rules = rules;
    }

    public RulesResponse(Status status, Error error, String message) {
        super(status, error, message);
        this.rules = null;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
