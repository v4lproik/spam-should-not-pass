package net.v4lproik.spamshouldnotpass.platform.models;

public abstract class Response {
    private String error;
    private Object response;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
