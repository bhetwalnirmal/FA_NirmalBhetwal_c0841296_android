package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.abstracts;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.daos.UserLocationDao;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;

@Database(entities = UserLocation.class, exportSchema = false, version = 1)
public abstract class UserLocationDatabase extends RoomDatabase {
    private static final String DB_NAME = "user_locations_db";
    private static UserLocationDatabase instance;

    public static synchronized UserLocationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserLocationDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract UserLocationDao userLocationDao();
}