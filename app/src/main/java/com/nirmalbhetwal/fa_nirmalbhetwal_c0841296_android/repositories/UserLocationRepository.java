package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.repositories;

import android.content.Context;

import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.abstracts.UserLocationDatabase;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.daos.UserLocationDao;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;

import java.util.List;

public class UserLocationRepository {
    private Context context;

    public UserLocationRepository () {
        this.context = context;
    }
    public List<UserLocation> getAllUserLocations () {
        return this.getRepository().getAllLocations();
    }

    public void insertUserLocation(UserLocation userLocation) {
        this.getRepository().insertUserLocation(userLocation);
    }

    public UserLocationDao getRepository () {
        return UserLocationDatabase.getInstance(context).userLocationDao();
    }
}
