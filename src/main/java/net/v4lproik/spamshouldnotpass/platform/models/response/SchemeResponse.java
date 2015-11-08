package net.v4lproik.spamshouldnotpass.platform.models.response;

public class SchemeResponse {

    private Object scheme;
    private Object error;

    public SchemeResponse(Object scheme, Object error) {
        this.scheme = scheme;
        this.error = error;
    }

    public Object getScheme() {
        return scheme;
    }

    public Object getError() {
        return error;
    }
}
