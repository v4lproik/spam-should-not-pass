package net.v4lproik.spamshouldnotpass.platform.models.entities;

public class AuthorMessageInfo {
    private String authorId;
    private String corporation;
    private String message;
    private Integer numberOfDocumentsSubmittedInTheLast5min;

    public AuthorMessageInfo() {
    }

    public AuthorMessageInfo(String authorId, String corporation, String message, Integer numberOfDocumentsSubmittedInTheLast5min) {
        this.authorId = authorId;
        this.corporation = corporation;
        this.message = message;
        this.numberOfDocumentsSubmittedInTheLast5min = numberOfDocumentsSubmittedInTheLast5min;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getCorporation() {
        return corporation;
    }

    public String getMessage() {
        return message;
    }

    public Integer getNumberOfDocumentsSubmittedInTheLast5min() {
        return numberOfDocumentsSubmittedInTheLast5min;
    }
}
