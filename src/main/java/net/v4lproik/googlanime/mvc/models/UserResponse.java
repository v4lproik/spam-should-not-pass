package net.v4lproik.googlanime.mvc.models;

public class UserResponse {

    private Object user;
    private Object token;
    private Object error;

    public UserResponse() {
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }
}
