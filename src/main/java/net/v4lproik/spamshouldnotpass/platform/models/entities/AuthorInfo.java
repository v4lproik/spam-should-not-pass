package net.v4lproik.spamshouldnotpass.platform.models.entities;

import java.util.List;

public class AuthorInfo {
    private String authorId;
    private String corporation;
    private List<String> messages;
    private Integer numberOfDocumentsSubmittedInTheLast5min;

    public AuthorInfo() {
    }

    public AuthorInfo(String authorId, String corporation, List<String> messages, Integer numberOfDocumentsSubmittedInTheLast5min) {
        this.authorId = authorId;
        this.corporation = corporation;
        this.messages = messages;
        this.numberOfDocumentsSubmittedInTheLast5min = numberOfDocumentsSubmittedInTheLast5min;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getCorporation() {
        return corporation;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Integer getNumberOfDocumentsSubmittedInTheLast5min() {
        return numberOfDocumentsSubmittedInTheLast5min;
    }
}
