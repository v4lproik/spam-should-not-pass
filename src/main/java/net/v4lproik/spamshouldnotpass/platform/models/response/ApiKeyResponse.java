package net.v4lproik.spamshouldnotpass.platform.models.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

final public class ApiKeyResponse extends PlatformResponse {

    private String key;

    public ApiKeyResponse(Status status, Error error, String message) {
        super(status, error, message);
    }

    public ApiKeyResponse(Status status) {
        super(status, null, null);
    }

    @JsonCreator
    public ApiKeyResponse(@JsonProperty("key") String key) {
        super();
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
