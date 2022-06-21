package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "user_locations")
public class UserLocation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;
    @ColumnInfo(name = "has_visited")
    private boolean hasVisitedTheLocation;
    @ColumnInfo(name = "created_at")
    private long createdAt;

    public UserLocation () {
        this.title = "";
        this.description = "";
        this.createdAt = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public LatLng getLatLng () {
        return new LatLng(latitude, longitude);
    }
}
