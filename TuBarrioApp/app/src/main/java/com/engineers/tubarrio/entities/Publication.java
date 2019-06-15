package com.engineers.tubarrio.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Publication implements Serializable{

    private int id;
    private String title;
    private String description;
    private Double longitude;
    private Double latitude;
    private Date updatedOn;
    private String username;
    private String userPhone;
    private String publicationImage;

    public Publication(JSONObject jsonObj) {
        try {
            //TODO
            this.id = jsonObj.has("Id")?jsonObj.getInt("Id"):0;
            this.title = jsonObj.has("Title")?jsonObj.getString("Title"):"";
            this.description = jsonObj.has("Description")?jsonObj.getString("Description"):"";
            this.longitude= jsonObj.has("Longitude")?jsonObj.getDouble("Longitude"):0;
            this.latitude= jsonObj.has("Latitude")?jsonObj.getDouble("Latitude"):0;
            this.publicationImage= jsonObj.has("PublicationImage")?jsonObj.getString("PublicationImage"):"";
            this.username = jsonObj.has("AuthorEmail")?jsonObj.getString("AuthorEmail"):"";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Publication(){
        this.id = 0;
        this.title = "";
        this.description = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.publicationImage = "";
        this.username = "";
        this.userPhone = "";
    }



    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return publicationImage;
    }

    public void setImage(String image) {
        this.publicationImage = image;
    }
}
