package com.example.giveandtake.adapter.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class User {
    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";
    public String profileImageURL="";
    public String volunteerStatus = "";
    public int countLike= 0;
    public  int countPlaces = 0;
    public String address= "";
    public String workAt = "";
    public String DateBirth = "";

    public User(){
    }

    public User(String Id, String name, String profileImageURL, String volunteerStatus, int countLike, int countPlaces, String address, String workAt, String dateBirth){
        this.id = Id;
        this.name = name;
        this.address = address;
        this.countLike = countLike;
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
