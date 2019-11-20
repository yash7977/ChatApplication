package com.example.chatapplication;

import android.net.Uri;

import java.io.Serializable;

public class UserDomain implements Serializable {


    String firstName;
    String lastName;
    String userName;
    String password;
    String profice_pic;


    public UserDomain( String firstName, String lastName, String userName, String password, String profice_pic) {
        //this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.profice_pic = profice_pic;
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
}

