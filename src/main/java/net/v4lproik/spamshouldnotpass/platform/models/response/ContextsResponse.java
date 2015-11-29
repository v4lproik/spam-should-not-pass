package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.dto.ContextDTO;

import java.util.List;

public class ContextsResponse extends PlatformResponse{

    private List<ContextDTO> contexts;

    public ContextsResponse(List<ContextDTO> contexts) {
        super();
        this.contexts = contexts;
    }

    public ContextsResponse(Status status, Error error, String message) {
        super(status, error, message);
        this.contexts = null;
    }

    public List<ContextDTO> getContexts() {
        return contexts;
    }
}
