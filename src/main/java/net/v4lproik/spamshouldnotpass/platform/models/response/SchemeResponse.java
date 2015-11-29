package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.dto.SchemeDTO;

public class SchemeResponse extends PlatformResponse{

    final private SchemeDTO scheme;

    public SchemeResponse(SchemeDTO scheme) {
        super();
        this.scheme = scheme;
    }

    public SchemeResponse(Status status, Error error, String message) {
        super(status, error, message);
        this.scheme = null;
    }

    public SchemeDTO getScheme() {
        return scheme;
    }
}
