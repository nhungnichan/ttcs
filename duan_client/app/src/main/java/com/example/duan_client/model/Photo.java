package com.example.duan_client.model;

public class Photo {
    private int resourceId;

    public Photo(int resourceId) {
        this.resourceId = resourceId;
    }

    public Photo() {
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
