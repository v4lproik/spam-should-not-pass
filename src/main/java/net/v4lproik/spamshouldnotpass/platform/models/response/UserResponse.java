package net.v4lproik.spamshouldnotpass.platform.models.response;

import net.v4lproik.spamshouldnotpass.platform.models.dto.BasicUserDTO;

final public class UserResponse extends PlatformResponse {

    private BasicUserDTO user;
    private String token;

    public UserResponse(Status status, Error error, String message) {
        super(status, error, message);
    }

    public UserResponse(Status status) {
        super(status, null, null);
    }

    public UserResponse(BasicUserDTO user, String token) {
        super();
        this.user = user;
        this.token = token;
    }

    public BasicUserDTO getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
