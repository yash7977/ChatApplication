package com.example.chatapplication;

public class TripDomain {
    Integer UniqueId;
    String Name;
    String Date;
    Integer createdBy;


    public TripDomain() {
    }

    public TripDomain(Integer uniqueId, String name, String date, Integer createdBy) {
        UniqueId = uniqueId;
        Name = name;
        Date = date;
        this.createdBy = createdBy;
    }


    public Integer getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        UniqueId = uniqueId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}
