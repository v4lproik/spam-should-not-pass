package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.dto.BasicUserDTO;

public class BasicUserResponse extends PlatformResponse {

    private BasicUserDTO user;

    public BasicUserResponse(BasicUserDTO user) {
        this.user = user;
    }

    public BasicUserResponse(Status status, Error error, String message) {
        super(status, error, message);
    }

    public BasicUserDTO getUser() {
        return user;
    }
}
