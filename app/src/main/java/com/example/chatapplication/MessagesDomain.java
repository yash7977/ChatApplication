package com.example.chatapplication;

public class MessagesDomain {

    Integer UniqueId;
    String message;
    Integer sentBy;


    public MessagesDomain(Integer uniqueId, String message, Integer sentBy) {
        UniqueId = uniqueId;
        this.message = message;
        this.sentBy = sentBy;
    }


    public MessagesDomain() {
    }


    public Integer getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        UniqueId = uniqueId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSentBy() {
        return sentBy;
    }

    public void setSentBy(Integer sentBy) {
        this.sentBy = sentBy;
    }
}
