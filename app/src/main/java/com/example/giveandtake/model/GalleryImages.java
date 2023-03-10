package com.example.giveandtake.model;

import android.net.Uri;

import com.example.giveandtake.adapter.GalleryAdapter;

public class GalleryImages {

    public Uri picUri;

    public GalleryImages(){

    }

    public GalleryImages(Uri picUri) {
        this.picUri = picUri;
    }

    public Uri getPicUri() {
        return picUri;
    }

    public void setPicUri(Uri picUri) {
        this.picUri = picUri;
    }




}
