package com.labodc.userprofile.messaging;

public class NotificationMessage {
    public Long userId;
    public String title;
    public String message;
    public String type;

    public NotificationMessage() {}

    public NotificationMessage(Long userId, String title, String message, String type) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
    }
}
