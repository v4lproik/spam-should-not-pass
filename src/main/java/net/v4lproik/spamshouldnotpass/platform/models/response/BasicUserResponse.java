package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;

public class BasicUserResponse extends PlatformResponse {

    private BasicMember user;

    public BasicUserResponse(BasicMember user) {
        this.user = user;
    }

    public BasicUserResponse(Status status, Error error, String message) {
        super(status, error, message);
    }

    public BasicMember getUser() {
        return user;
    }
}
