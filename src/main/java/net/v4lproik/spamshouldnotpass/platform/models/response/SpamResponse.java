package net.v4lproik.spamshouldnotpass.platform.models.response;

public class SpamResponse {

    private String isSpam;
    private String reason;

    public SpamResponse() {
    }

    public SpamResponse(String isSpam, String reason) {
        this.isSpam = isSpam;
        this.reason = reason;
    }

    public String getIsSpam() {
        return isSpam;
    }

    public String getReason() {
        return reason;
    }
}
