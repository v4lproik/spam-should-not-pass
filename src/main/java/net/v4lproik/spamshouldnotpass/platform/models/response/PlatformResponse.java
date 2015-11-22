package net.v4lproik.spamshouldnotpass.platform.models.response;

public class PlatformResponse {

    public enum Status{
        OK,
        NOK
    }

    public enum Error{
        UNKNOWN,
        INVALID_INPUT,
        INVALID_PERMISSION
    }

    private Status status;
    private Error error;
    private String message;

    public PlatformResponse() {
        this.status = Status.OK;
        this.error = null;
        this.message = null;
    }

    public PlatformResponse(Status status, Error error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public Error getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
