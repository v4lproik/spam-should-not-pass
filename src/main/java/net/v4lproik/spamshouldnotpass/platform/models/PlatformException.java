package net.v4lproik.spamshouldnotpass.platform.models;

import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;

import java.io.Serializable;

public class PlatformException extends Exception implements Serializable {

    PlatformResponse.Error platformError;

    public PlatformException() {
        super();
    }
    public PlatformException(String msg)   {
        super(msg);
    }
    public PlatformException(PlatformResponse.Error platformError, String msg)   {
        super(msg);
        this.platformError = platformError;
    }
    public PlatformException(String msg, Exception e)  {
        super(msg, e);
    }

    public PlatformResponse.Error getPlatformError() {
        return platformError;
    }
}
