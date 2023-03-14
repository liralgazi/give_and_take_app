package com.example.giveandtake.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class HomeModel {
    private static final HomeModel _instance = new HomeModel();
    private String userName, profileImage, postImage, uid,  postText, comments , postId;
    @ServerTimestamp
    private Date timestamp;
    private  int likeCount;

    public static HomeModel instance(){
        return _instance;
    }

    public void getAllPosts(){

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

    public HomeModel(){

    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public HomeModel(String userName, Date timestamp, String profileImage, String postImage, int likeCount, String uid, String postText, String comments , String postId ) {
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

}
