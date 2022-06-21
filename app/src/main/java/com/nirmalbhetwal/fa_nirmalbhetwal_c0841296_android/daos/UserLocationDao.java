package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;

import java.util.List;

@Dao
public interface UserLocationDao {
    @Query("SELECT * FROM user_locations")
    List<UserLocation> getAllLocations();

    @Query("Select * from user_locations where title like '%' || :search || '%' or description like '%' || :search || '%'" )
    List<UserLocation> getFilteredLocations(String search);

    @Insert
    void insertUserLocation(UserLocation userLocation);

    @Update
    void updateUserLocation(UserLocation userLocation);
}
