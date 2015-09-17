package net.v4lproik.googlanime.mvc.models;

/**
 * Created by joel on 13/05/2015.
 */
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
