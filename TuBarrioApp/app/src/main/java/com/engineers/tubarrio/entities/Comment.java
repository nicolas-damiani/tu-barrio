package com.engineers.tubarrio.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Comment {
    private String text;
    private Date createdOn;
    private User creator;


    public Comment(JSONObject jsonObj) {
        try {
            this.text = jsonObj.has("Text")?jsonObj.getString("Text"):"";
            //  this.createdOn= jsonObj.has("CreatedOn")?jsonObj.getString("Title"):"";
            this.creator = new User(new JSONObject(jsonObj.getString("User")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
