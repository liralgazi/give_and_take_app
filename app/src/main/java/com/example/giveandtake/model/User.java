package com.example.giveandtake.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.giveandtake.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";
    public String profileImageURL="";
    public String volunteerStatus = "";
    public Integer numFriends= 0;
    public  Integer countPlaces = 0;
    public String address= "";
    public String workAt = "";
    public String dateBirth = "";
    //public ArrayList<String> friends;
    public Long lastUpdated;

    static final String COLLECTION = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "students_local_last_update";

//    public ArrayList<String> getFriends() {
//        return friends;
//    }
//
//    public void setFriends(ArrayList<String> friends) {
//        this.friends = friends;
//    }

    public User(){
    }

    public User(String Id, String name, String profileImageURL, String volunteerStatus, Integer numFriends, Integer countPlaces, String address, String workAt, String dateBirth){
        this.id = Id;
        this.name = name;
        this.address = address;
        this.numFriends = numFriends;
        this.countPlaces = countPlaces;
        this.dateBirth = dateBirth;
        this.profileImageURL = profileImageURL;
        this.workAt = workAt;
        this.volunteerStatus = volunteerStatus;
        //this.friends = friends;
    }

    static public String ID="id";
    static public String NAME="name";
    static public String PROFILEIMAGE="profileImageURL";
    static public String VOLUNTEERSTATUS = "volunteerStatus";
    static public int NUMFRIEND= 0;
    static public  int COUNTPLACES = 0;
    static public String ADDRESS= "address";
    static public String WORKAT = "workAt";
    static public String DATEBIRTH = "dateBirth";
    //static  public String FRIENDS = "friends";

    public static User fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        String profileImage = (String)json.get(PROFILEIMAGE);
        String volunteer = (String) json.get(VOLUNTEERSTATUS);
        String address = (String) json.get(ADDRESS);
        String workAt = (String) json.get(WORKAT);
        String dateBirth = (String) json.get(DATEBIRTH);
        Integer numFriends = (Integer) json.get(NUMFRIEND);
        Integer countPlaces = (Integer) json.get(COUNTPLACES);
        //ArrayList<String> friends = (ArrayList<String>)json.get(FRIENDS);

        User st = new User(id,name, profileImage, volunteer, numFriends, countPlaces, address,workAt, dateBirth);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            st.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }
        return st;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(PROFILEIMAGE, getProfileImageURL());
        json.put(volunteerStatus, getVolunteerStatus());
        json.put(String.valueOf(numFriends), getNumFriends());
        json.put(String.valueOf(countPlaces), getCountPlaces());
        json.put(address, getAddress());
        json.put(workAt, getWorkAt());
        json.put(dateBirth, getDateBirth());
        //json.put(friends.toString(),getFriends() );
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
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
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        dateBirth = dateBirth;
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


    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

}
