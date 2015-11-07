package net.v4lproik.spamshouldnotpass.platform.models;

import java.io.Serializable;

public class BackendException extends Exception implements Serializable
{
    public BackendException() {
        super();
    }
    public BackendException(String msg)   {
        super(msg);
    }
    public BackendException(String msg, Exception e)  {
        super(msg, e);
    }
}
