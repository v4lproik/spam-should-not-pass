package net.v4lproik.spamshouldnotpass.platform.models.response;

public class SpamResponse {

    private Object isSpam;
    private Object reason;

    public SpamResponse(Object isSpam, Object reason) {
        this.isSpam = isSpam;
        this.reason = reason;
    }

    public Object getIsSpam() {
        return isSpam;
    }

    public Object getReason() {
        return reason;
    }
}
