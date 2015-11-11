package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;

import java.util.List;

public class RulesResponse {

    private List<Rule> rules;

    public RulesResponse(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
