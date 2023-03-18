package com.example.giveandtake.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonReader;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    public String postImage, postText;
    @ServerTimestamp
    public Date timestamp;
    public Long likeCount;
    public User userPost;
    public Long lastUpdated;

    public User getUserPost() {
        return userPost;
    }

    public void setUserPost(User userPost) {
        this.userPost = userPost;
    }

    public Post(User userPost, String postImage, Long likeCount, String postText , String postId) {
       this.userPost = userPost;
        this.postImage = postImage;
        this.likeCount = likeCount;
        this.postText = postText;
        this.postId = postId;
    }


    static final String POSTID = "postId";
    static final User USERPOST = new User();
    static final String POSTIMAGE = "postImage";
    static final String LIKECOUNT = "likeCount";
    static final String POSTEXT = "postText";
    static final String POSTLIKETEXT = "postLikeText";
    static final String COLLECTION = "posts";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";

    public static Post fromJson(Map<String,Object> json){
        String postId = (String)json.get(POSTID);
        User userPost = (User)json.get(USERPOST);
        String postImage = (String)json.get(POSTIMAGE);
        String postText = (String)json.get(POSTEXT);
        Long likeCount = (Long) json.get(LIKECOUNT);
        String likeText = (String)json.get(POSTLIKETEXT);

        Post post = new Post(userPost,postImage,likeCount, postText,postId);
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
        json.put(POSTID, getPostId());
        json.put(String.valueOf((User)USERPOST),getUserPost());
        json.put(POSTIMAGE, getPostImage());
        json.put(POSTEXT, getPostText());
        json.put(String.valueOf(LIKECOUNT), getLikeCount());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
