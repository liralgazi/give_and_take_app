package com.example.giveandtake.model;

import android.net.Uri;

import com.example.giveandtake.adapter.GalleryAdapter;

public class GalleryImages {

    private Uri picUri;
    private String data;

    public GalleryImages(){

    }

    public GalleryImages(Uri picUri, String data) {
        this.picUri = picUri;
        this.data = data;
    }

    public Uri getPicUri() {
        return picUri;
    }

    public void setPicUri(Uri picUri) {
        this.picUri = picUri;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
