package com.engineers.tubarrio.entities;

import android.hardware.usb.UsbRequest;

import org.json.JSONArray;
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
    private User creator;
    private ArrayList<User> followers;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    private String publicationImage;

    public Publication(JSONObject jsonObj) {
        try {
            this.id = jsonObj.has("Id")?jsonObj.getInt("Id"):0;
            this.title = jsonObj.has("Title")?jsonObj.getString("Title"):"";
            this.description = jsonObj.has("Description")?jsonObj.getString("Description"):"";
            this.longitude= jsonObj.has("Longitude")?jsonObj.getDouble("Longitude"):0;
            this.latitude= jsonObj.has("Latitude")?jsonObj.getDouble("Latitude"):0;
            this.publicationImage= jsonObj.has("PublicationImage") && !jsonObj.getString("PublicationImage").equals("null")?jsonObj.getString("PublicationImage"):"";
            this.creator = new User(new JSONObject(jsonObj.getString("Creator")));
            this.followers = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonObj.getString("Followers"));

            if (jsonArray.length() != 0) {
                ArrayList<User> userList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userObj = jsonArray.getJSONObject(i);
                    User user = new User(userObj);
                    userList.add(user);
                }
                this.followers = userList;
            }
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
        this.followers = new ArrayList<User>();
    }



    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

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

    public String getImage() {
        return publicationImage;
    }

    public void setImage(String image) {
        this.publicationImage = image;
    }
    public String getPublicationImage() {
        return publicationImage;
    }

    public void setPublicationImage(String publicationImage) {
        this.publicationImage = publicationImage;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }
}
