package com.example.giveandtake.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceSearchResults {
    @SerializedName("Search")
    List<Place> search;

    public List<Place> getSearch() {
        return search;
    }

    public void setSearch(List<Place> search) {
        this.search = search;
    }
}
