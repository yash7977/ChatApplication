package com.example.chatapplication;

import android.content.Intent;
import android.net.Uri;

import java.io.Serializable;
import java.util.UUID;

public class UserDomain implements Serializable {


    String firstName;
    String lastName;
    String email;
    String userName;
    String password;
    String profice_pic;
    String uniqueId ;


    public UserDomain(String firstName, String lastName, String email, String userName, String password, String profice_pic, String uniqueId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.profice_pic = profice_pic;
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return "UserDomain{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", profice_pic='" + profice_pic + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfice_pic() {
        return profice_pic;
    }

    public void setProfice_pic(String profice_pic) {
        this.profice_pic = profice_pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}

