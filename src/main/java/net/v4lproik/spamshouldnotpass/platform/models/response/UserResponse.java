package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.entities.User;

public class UserResponse {

    private User user;
    private String token;
    private String error;

    public UserResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
