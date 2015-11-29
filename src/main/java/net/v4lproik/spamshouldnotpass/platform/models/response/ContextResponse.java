package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;

import java.util.List;

public class ContextResponse extends PlatformResponse{

    final private Context context;

    final private List<Rule> rules;

    public ContextResponse(Context context, List<Rule> rules) {
        super();
        this.context = context;
        this.rules = rules;
    }

    public ContextResponse(Status status, Error error, String message) {
        super(status, error, message);
        this.context = null;
        this.rules = null;
    }

    public Context getContext() {
        return context;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
