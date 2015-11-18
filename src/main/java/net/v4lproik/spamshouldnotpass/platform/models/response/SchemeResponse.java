package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;

public class SchemeResponse extends PlatformResponse{

    final private Scheme scheme;

    public SchemeResponse(Scheme scheme) {
        super();
        this.scheme = scheme;
    }

    public SchemeResponse(Status status, Error error, String message) {
        super(status, error, message);
        this.scheme = null;
    }

    public Scheme getScheme() {
        return scheme;
    }
}
