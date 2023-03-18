package com.example.giveandtake.model;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.giveandtake.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "User")
public class User {
    @PrimaryKey
    @NonNull
    public String uid="";
    public String name="";
    public String profileImageURL="";
    public String volunteerStatus = "";
    public String address= "";
    public String workAt = "";
    public String age = "";
    public Long lastUpdated;

    static final String COLLECTION = "User";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";

    public User(String uid, String name, String profileImageURL, String volunteerStatus, String address, String workAt, String age){
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.age = age;
        this.profileImageURL = profileImageURL;
        this.workAt = workAt;
        this.volunteerStatus = volunteerStatus;
    }

    static public String UID="uid";
    static public String NAME="name";
    static public String PROFILEIMAGE="profileImageURL";
    static public String VOLUNTEERSTATUS = "volunteerStatus";
    static public String ADDRESS= "address";
    static public String WORKAT = "workAt";
    static public String AGE = "age";

    public User() {

    }

    public static User fromJson(Map<String,Object> json){
        String uid = (String)json.get(UID);
        String name = (String)json.get(NAME);
        String profileImage = (String)json.get(PROFILEIMAGE);
        String volunteer = (String) json.get(VOLUNTEERSTATUS);
        String address = (String) json.get(ADDRESS);
        String workAt = (String) json.get(WORKAT);
        String age = (String) json.get(AGE);

        User user = new User(uid,name, profileImage, volunteer, address,workAt, age);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }
        return user;
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
        json.put(UID, getId());
        json.put(NAME, getName());
        json.put(PROFILEIMAGE, getProfileImageURL());
        json.put(volunteerStatus, getVolunteerStatus());
        json.put(address, getAddress());
        json.put(workAt, getWorkAt());
        json.put(age, getAge());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    @NonNull
    public String getId() {
        return uid;
    }

    public void setId(@NonNull String id) {
        this.uid = id;
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public  String getAge() {
        return age;
    }

    public  void setAge(String age) {
        this.age=age;
    }

}
