package com.example.giveandtake.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class PostImageActivity {

    private String imageUrl, id, description;

    @ServerTimestamp
    private Date timestamp;

    public PostImageActivity(){

    }

    public PostImageActivity(String imageUrl,String id, Date timestamp, String description){

        this.imageUrl = imageUrl;
        this.id = id;
        this.timestamp = timestamp;
        this.description = description;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return this.description;
    }
}
