package net.v4lproik.googlanime.mvc.models;

public class TokenResponse {

    private Object token;
    private Object error;

    public TokenResponse() {
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
