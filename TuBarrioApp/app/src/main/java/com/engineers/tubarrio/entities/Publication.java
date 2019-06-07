package com.engineers.tubarrio.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Publication {

    private String title;
    private String description;
    private Double longitude;
    private Double latitude;
    private Date updatedOn;
    private String username;
    private ArrayList<EncodedImage> images;

    public Publication(JSONObject jsonObj) {
        try {
            //TODO
            this.title = jsonObj.has("Title")?jsonObj.getString("Title"):"";
            this.description = jsonObj.has("Description")?jsonObj.getString("Description"):"";
            this.longitude= jsonObj.has("Longitude")?jsonObj.getDouble("Longitude"):0;
            this.latitude= jsonObj.has("Latitude")?jsonObj.getDouble("Latitude"):0;
          //  this.updatedOn= jsonObj.has("Title")?jsonObj.getString("Title"):"";
            this.username = jsonObj.has("AuthorEmail")?jsonObj.getString("AuthorEmail"):"";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
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

    public ArrayList<EncodedImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<EncodedImage> images) {
        this.images = images;
    }
}
