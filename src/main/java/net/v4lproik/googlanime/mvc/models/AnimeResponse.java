package net.v4lproik.googlanime.mvc.models;

public class AnimeResponse {

    private Object animes;
    private Object error;

    public AnimeResponse() {
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
