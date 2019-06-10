package com.engineers.tubarrio.entities;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public String firstName;
    public String lastName;
    public String email;
    public int id;
    private String profileImage;
    private String phone;


    public User (){
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "";
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User (JSONObject userJson){
        try {
            this.firstName = userJson.has("Name") ? userJson.getString("Name"): "";
            this.lastName= userJson.has("Surname") ? userJson.getString("Surname"): "";
            this.email = userJson.has("Email") ? userJson.getString("Email"): "";
            this.profileImage = userJson.has("ProfileImage") ? userJson.getString("ProfileImage"): "";
            this.phone =userJson.has("Phone") ? userJson.getString("Phone"): "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

}
