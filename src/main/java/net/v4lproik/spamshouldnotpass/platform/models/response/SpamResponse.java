package net.v4lproik.spamshouldnotpass.platform.models.response;

import com.google.common.base.Objects;

public class SpamResponse extends PlatformResponse{

    private String isSpam;
    private String reason;

    public SpamResponse() {
    }

    public SpamResponse(String isSpam, String reason) {
        super();
        this.isSpam = isSpam;
        this.reason = reason;
    }

    public SpamResponse(Status status, Error error, String message) {
        super(status, error, message);
        this.isSpam = null;
        this.reason = null;
    }

    public String getIsSpam() {
        return isSpam;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("isSpam", isSpam)
                .add("reason", reason)
                .toString();
    }
}
