package com.engineers.tubarrio.entities;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public boolean hasCompletedProfile;
    public int cantPublications;
    public int cantFollowed;
    public int cantComments;

    public User (JSONObject userJson){
        try {
            this.id = userJson.has("id") ? userJson.getInt("id"): 0 ;
            this.firstName = userJson.has("first_name") ? userJson.getString("first_name"): "";
            this.lastName= userJson.has("last_name") ? userJson.getString("last_name"): "";
            this.email = userJson.has("email") ? userJson.getString("email"): "";
            this.hasCompletedProfile = userJson.has("has_completed_profile") && userJson.getInt("has_completed_profile") ==  1;
            this.cantComments = userJson.has("cant_comments") ? userJson.getInt("cant_comments"): 0 ;
            this.cantPublications = userJson.has("cant_publications") ? userJson.getInt("cant_publications") : 0;
            this.cantFollowed = userJson.has("cant_followed") ? userJson.getInt("cant_followed") : 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasCompletedProfile() {
        return hasCompletedProfile;
    }

    public void setHasCompletedProfile(boolean hasCompletedProfile) {
        this.hasCompletedProfile = hasCompletedProfile;
    }

    public User (){
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.cantFollowed = 0;
        this.cantPublications = 0;
        this.cantComments = 0;
    }
}
