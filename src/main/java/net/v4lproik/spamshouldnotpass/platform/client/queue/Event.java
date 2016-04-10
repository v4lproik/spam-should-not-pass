package net.v4lproik.spamshouldnotpass.platform.client.queue;

public class Event {
    private String body;

    public Event(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
