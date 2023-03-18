package com.example.giveandtake.fragments;


import androidx.fragment.app.Fragment;

public class PlaceListFragment extends Fragment {


    public String id;
    public String name;
    public String address;
    public String description;
    public String city;
    public String imageURL;

    public PlaceListFragment(){}

    public PlaceListFragment(String id, String name, String address, String description, String city, String imageURL) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.city = city;
        this.imageURL = imageURL;
    }

    public String getUid()
    {
        return this.id;
    }



    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}
