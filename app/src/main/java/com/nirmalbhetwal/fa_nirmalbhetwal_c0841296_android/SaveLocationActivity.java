package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.ui.home.HomeFragment;

public class SaveLocationActivity extends AppCompatActivity {
    private String address = "";
    private LatLng currentLatLng;
    EditText etTitle;
    private MapView mMapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);
        etTitle = (EditText) findViewById(R.id.locationTitle);

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

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("Title").snippet("Marker Description"));
                // For zooming functionality
                CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLatLng).zoom(5).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        mapFragment.getView().setClickable(false);
    }

    private void getLocationValues() {
        address = getIntent().getStringExtra(HomeFragment.DECODED_ADDRESS);
        currentLatLng = (LatLng) getIntent().getExtras().getParcelable(HomeFragment.LOCATION_TO_BE_SAVED);
    }
}