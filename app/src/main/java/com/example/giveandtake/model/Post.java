package com.example.giveandtake.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.giveandtake.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String postId;
    public String userName, profileImage, postImage, uid,  postText, comments;
    @ServerTimestamp
    public Date timestamp;
    public int likeCount;
    public Long lastUpdated;

    public Post(String userName, Date timestamp, String profileImage, String postImage, int likeCount, String uid, String postText, String comments , String postId ) {
        this.userName = userName;
        this.timestamp = timestamp;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.likeCount = likeCount;
        this.uid = uid;
        this.postText = postText;
        this.comments = comments;
        this.postId = postId;
    }

    static final String USERNAME = "userName";
    static final String TIMESTAMP = "timestamp";
    static final String POSTID = "postId";
    static final String PROFILEIMAGE = "profileImage";
    static final String POSTIMAGE = "postImage";
    static final String LIKECOUNT = "likeCount";
    static final String UID = "uid";
    static final String POSTEXT = "postText";
    static final String COMMENTS = "comments";
    static final String COLLECTION = "posts";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "students_local_last_update";

    public static Post fromJson(Map<String,Object> json){
        String postId = (String)json.get(POSTID);
        Date timestamp = (Date)json.get(TIMESTAMP);
        String name = (String)json.get(USERNAME);
        String profileImage = (String)json.get(PROFILEIMAGE);
        String postImage = (String)json.get(POSTIMAGE);
        String uid = (String)json.get(UID);
        String postText = (String)json.get(POSTEXT);
        String comments = (String)json.get(COMMENTS);
        int likeCount = (Integer) json.get(LIKECOUNT);

        Post post = new Post(name,timestamp,profileImage,postImage,likeCount,uid,postText,comments,postId);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            post.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }
        return post;
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
        json.put(USERNAME, getUserName());
        json.put(TIMESTAMP, getTimestamp());
        json.put(POSTID, getPostId());
        json.put(PROFILEIMAGE, getProfileImage());
        json.put(POSTIMAGE, getPostImage());
        json.put(UID, getUid());
        json.put(POSTEXT, getPostText());
        json.put(COMMENTS, getComments());
        json.put(String.valueOf(LIKECOUNT), getLikeCount());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
