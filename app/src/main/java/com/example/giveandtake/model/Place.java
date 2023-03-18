package com.example.giveandtake.model;

public class Place {

    String name;
    String activeProjects;
    String city;
    int id;
    int totalProjects;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return activeProjects;
    }

    public void setAddress(String activeProjects) {
        this.activeProjects = activeProjects;
    }

    public String getDescription() {
        return city;
    }

    public void setDescription(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembersNum() {
        return totalProjects;
    }

    public void setMembersNum(int totalProjects) {
        this.totalProjects = totalProjects;
    }


}
