package net.v4lproik.spamshouldnotpass.platform.models.response;

public class RulesResponse {

    private Object rules;

    public RulesResponse(Object rules) {
        this.rules = rules;
    }

    public Object getRules() {
        return rules;
    }
}
