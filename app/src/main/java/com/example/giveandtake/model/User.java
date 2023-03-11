package com.example.giveandtake.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Map;

@Entity
public class User {
    public static final String COLLECTION = "users";
    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";
    public String profileImageURL="";
    public String volunteerStatus = "";
    public int numFriends= 0;
    public  int countPlaces = 0;
    public String address= "";
    public String workAt = "";
    public String DateBirth = "";

    public User(){
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getVolunteerStatus() {
        return volunteerStatus;
    }

    public void setVolunteerStatus(String volunteerStatus) {
        this.volunteerStatus = volunteerStatus;
    }

    public int getNumFriends() {
        return numFriends;
    }

    public void setNumFriends(int numFriends) {
        this.numFriends = numFriends;
    }

    public int getCountPlaces() {
        return countPlaces;
    }

    public void setCountPlaces(int countPlaces) {
        this.countPlaces = countPlaces;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkAt() {
        return workAt;
    }

    public void setWorkAt(String workAt) {
        this.workAt = workAt;
    }

    public String getDateBirth() {
        return DateBirth;
    }

    public void setDateBirth(String dateBirth) {
        DateBirth = dateBirth;
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

    public User(String Id, String name, String profileImageURL, String volunteerStatus, int numFriends, int countPlaces, String address, String workAt, String dateBirth){
        this.id = Id;
        this.name = name;
        this.address = address;
        this.numFriends = numFriends;
        this.countPlaces = countPlaces;
        this.DateBirth = dateBirth;
        this.profileImageURL = profileImageURL;
        this.workAt = workAt;
    }

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

}
