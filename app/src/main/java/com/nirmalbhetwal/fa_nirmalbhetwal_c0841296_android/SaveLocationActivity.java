package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.abstracts.UserLocationDatabase;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.daos.UserLocationDao;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.repositories.UserLocationRepository;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.ui.home.HomeFragment;

import java.util.List;

public class SaveLocationActivity extends AppCompatActivity {
    private String address = "";
    private LatLng currentLatLng;
    EditText etTitle, etDescription;
    private MapView mMapView;
    private GoogleMap googleMap;
    private UserLocation userLocation;
    Switch isFavourite, hasVisited;
    Button saveLocation;
    UserLocationRepository userLocationRepository;
    UserLocationDatabase appDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);
        etTitle = (EditText) findViewById(R.id.locationTitle);
        etDescription = (EditText) findViewById(R.id.locationDescription);
        hasVisited = (Switch) findViewById(R.id.hasVisitedTheLocation);
        isFavourite = (Switch) findViewById(R.id.isFavouriteLocation);
        saveLocation = (Button) findViewById(R.id.saveMapLocation);

        appDB = UserLocationDatabase.getInstance(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewSaveLocation);

        getLocationValues();

        if(!address.equals("")) {
            Log.d("TAG", address);
            Log.d("TAG", currentLatLng.latitude + ": " + currentLatLng.longitude);
            etTitle.setText(address);
        } else {
            etTitle.setHint("Location not found");
        }

        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserLocationToDatabase();
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("Title").snippet("Marker Description"));
                // For zooming functionality
                CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLatLng).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        mapFragment.getView().setClickable(false);
    }

    private void saveUserLocationToDatabase() {
            userLocation = new UserLocation();
            userLocation.setLatitude(currentLatLng.latitude);
            userLocation.setLongitude(currentLatLng.longitude);
            userLocation.setTitle(address);
            userLocation.setDescription(etDescription.getText().toString().trim());
            userLocation.setFavourite(isFavourite.isChecked());
            userLocation.setHasVisitedTheLocation(hasVisited.isChecked());
            getUserLocationRepository().insertUserLocation(userLocation);

            List<UserLocation> list = this.getUserLocationRepository().getAllLocations();
            for(UserLocation location : list) {
                Log.d("TAG", location.getTitle() + location.getDescription());
            }
    }

    private void getLocationValues() {
        address = getIntent().getStringExtra(HomeFragment.DECODED_ADDRESS);
        currentLatLng = (LatLng) getIntent().getExtras().getParcelable(HomeFragment.LOCATION_TO_BE_SAVED);
    }

    private UserLocationDao getUserLocationRepository () {
        return appDB.userLocationDao();
    }
}