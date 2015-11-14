package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;

public class BasicUserResponse {

    private BasicMember user;
    private String error;

    public BasicUserResponse() {
    }

    public BasicMember getUser() {
        return user;
    }

    public void setUser(BasicMember user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
