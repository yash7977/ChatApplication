package com.example.chatapplication;

public class MessagesDomain {

    String UniqueId;
    String message;
    String sentBy;


    public MessagesDomain(String uniqueId, String message, String sentBy) {
        UniqueId = uniqueId;
        this.message = message;
        this.sentBy = sentBy;
    }


    public MessagesDomain() {
    }


    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
