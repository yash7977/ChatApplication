package com.example.chatapplication;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TripDomain {
    String UniqueId;
    String Name;
    String CoverPic;
    Integer createdBy;
    LatLng start_postion;
    LatLng destination;
    ArrayList<MessagesDomain> messagesDomains = new ArrayList<>();
    ArrayList<String> users = new ArrayList<>();




    public TripDomain() {
    }

    public TripDomain(String uniqueId, String name, String coverPic, Integer createdBy, LatLng start_postion, LatLng destination, ArrayList<MessagesDomain> messagesDomains, ArrayList<String> users) {
        UniqueId = uniqueId;
        Name = name;
        CoverPic = coverPic;
        this.createdBy = createdBy;
        this.start_postion = start_postion;
        this.destination = destination;
        this.messagesDomains = messagesDomains;
        this.users = users;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public ArrayList<MessagesDomain> getMessagesDomains() {
        return messagesDomains;
    }

    public void setMessagesDomains(ArrayList<MessagesDomain> messagesDomains) {
        this.messagesDomains = messagesDomains;
    }

    public MessagesDomain sendMessage(MessagesDomain messagesDomain){
        this.messagesDomains.add(messagesDomain);
        return messagesDomain;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }


    public String getCoverPic() {
        return CoverPic;
    }

    public void setCoverPic(String coverPic) {
        CoverPic = coverPic;
    }

    public LatLng getStart_ostion() {
        return start_postion;
    }

    public void setStart_ostion(LatLng start_postion) {
        this.start_postion = start_postion;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "TripDomain{" +
                "UniqueId='" + UniqueId + '\'' +
                ", Name='" + Name + '\'' +
                ", CoverPic='" + CoverPic + '\'' +
                ", createdBy=" + createdBy +
                ", start_postion=" + start_postion +
                ", destination=" + destination +
                ", messagesDomains=" + messagesDomains +
                ", users=" + users +
                '}';
    }
}
