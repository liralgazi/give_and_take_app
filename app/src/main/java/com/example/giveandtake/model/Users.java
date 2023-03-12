package com.example.giveandtake.model;

public class Users {

    private String email, name, profileImage, uid, volunteerStatus;

    public Users(String email, String name, String profileImage, String uid, String volunteerStatus) {
        this.email = email;
        this.name = name;
        this.volunteerStatus = volunteerStatus;
        this.profileImage = profileImage;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVolunteerStatus() {
        return volunteerStatus;
    }

    public void setVolunteerStatus(String volunteerStatus) {
        this.volunteerStatus = volunteerStatus;
    }
}
