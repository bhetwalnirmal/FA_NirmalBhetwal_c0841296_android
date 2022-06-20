package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class UserLocation {
    private LatLng latLng;
    private String title;
    private String description;
    private boolean isFavourite;
    private boolean hasVisitedTheLocation;
    private Date createdAt;

    public UserLocation () {
        this.latLng = null;
        this.title = "";
        this.description = "";
        this.createdAt = new Date();
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isHasVisitedTheLocation() {
        return hasVisitedTheLocation;
    }

    public void setHasVisitedTheLocation(boolean hasVisitedTheLocation) {
        this.hasVisitedTheLocation = hasVisitedTheLocation;
    }
}
