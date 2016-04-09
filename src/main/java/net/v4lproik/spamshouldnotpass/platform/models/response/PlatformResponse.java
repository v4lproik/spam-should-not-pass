package net.v4lproik.spamshouldnotpass.platform.models.response;

import com.google.common.base.Objects;

public class PlatformResponse {

    public enum Status{
        OK,
        NOK
    }

    public enum Error{
        UNKNOWN,
        NOT_FOUND,
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

    public PlatformResponse(Error error, String message) {
        this.error = error;
        this.message = message;
    }

    public PlatformResponse(Status status, Error error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public static PlatformResponse ok(){
        return new PlatformResponse();
    }

    public static PlatformResponse nok(Error error, String message){
        return new PlatformResponse(error, message);
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("status", status)
                .add("error", error)
                .add("message", message)
                .toString();
    }
}
