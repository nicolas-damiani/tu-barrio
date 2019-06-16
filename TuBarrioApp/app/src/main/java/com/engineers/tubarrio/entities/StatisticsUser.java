package com.engineers.tubarrio.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class StatisticsUser {

    private int cantPublications;
    private int cantFollowedPublications;
    private int cantComments;


    public StatisticsUser (){
        this.cantPublications = 0;
        this.cantFollowedPublications = 0;
        this.cantComments = 0;
    }

    public StatisticsUser (JSONObject userJson){
        try {
            this.cantPublications = userJson.has("CantPublications") ? userJson.getInt("CantPublications"): 0;
            this.cantFollowedPublications = userJson.has("CantFollowedPublications") ? userJson.getInt("CantFollowedPublications"): 0;
            this.cantComments = userJson.has("CantComments") ? userJson.getInt("CantComments"): 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCantPublications() {
        return cantPublications;
    }

    public void setCantPublications(int cantPublications) {
        this.cantPublications = cantPublications;
    }

    public int getCantFollowedPublications() {
        return cantFollowedPublications;
    }

    public void setCantFollowedPublications(int cantFollowedPublications) {
        this.cantFollowedPublications = cantFollowedPublications;
    }

    public int getCantComments() {
        return cantComments;
    }

    public void setCantComments(int cantComments) {
        this.cantComments = cantComments;
    }
}
