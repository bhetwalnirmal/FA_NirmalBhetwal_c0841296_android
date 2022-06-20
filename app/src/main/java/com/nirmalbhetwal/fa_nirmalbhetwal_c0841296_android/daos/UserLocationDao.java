package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;

import java.util.List;

@Dao
public interface UserLocationDao {
    @Query("SELECT * FROM user_locations")
    List<UserLocation> getAllLocations();

    @Insert
    void insertUserLocation(UserLocation userLocation);
}
