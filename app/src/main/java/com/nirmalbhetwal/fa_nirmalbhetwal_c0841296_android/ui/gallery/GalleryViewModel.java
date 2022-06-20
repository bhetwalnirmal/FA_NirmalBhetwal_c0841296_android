package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.ui.gallery;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.abstracts.UserLocationDatabase;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;

import java.util.List;

public class GalleryViewModel extends ViewModel {

    MutableLiveData<List<UserLocation>> userLocationLiveData;
    List<UserLocation> userLocationList;
    UserLocationDatabase appDB;

    public GalleryViewModel(Context context) {
        appDB = UserLocationDatabase.getInstance(context);
        userLocationLiveData = new MutableLiveData<>();
        init();
    }

    private void init() {
        userLocationList = appDB.userLocationDao().getAllLocations();
        userLocationLiveData.setValue(userLocationList);
    }
}