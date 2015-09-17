package net.v4lproik.googlanime.mvc.models;

/**
 * Created by joel on 07/05/2015.
 */
public class JSONResponse {

    private Object animes;
    private Object error;

    public JSONResponse() {
    }

    public Object getAnimes() {
        return animes;
    }

    public void setAnimes(Object animes) {
        this.animes = animes;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
