package com.engineers.tubarrio.entities;

import org.json.JSONObject;

public class Publication {

    private String title;
    private String fileUrl;

    public Publication(JSONObject jsonObj) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
