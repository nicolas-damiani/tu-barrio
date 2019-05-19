package com.engineers.tubarrio.entities;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public int id;
    public String firstName;
    public String lastName;
    public String email;

    public User (JSONObject userJson){
        try {
            this.id = userJson.has("id") ? userJson.getInt("id"): 0 ;
            this.firstName = userJson.has("first_name") ? userJson.getString("first_name"): "";
            this.lastName= userJson.has("last_name") ? userJson.getString("last_name"): "";
            this.email = userJson.has("email") ? userJson.getString("email"): "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User (){
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
    }
}
