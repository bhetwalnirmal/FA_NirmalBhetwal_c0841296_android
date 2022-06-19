package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.R;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private MapView mMapView;
    private GoogleMap googleMap;
    private List<String> permissions = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

                if (shouldShowRequestPermissionRationale(permissions.get(0))) {
                    googleMap.setMyLocationEnabled(true);
                    //To add marker
                    LatLng sydney = new LatLng(-34, 151);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Title").snippet("Marker Description"));
                    // For zooming functionality
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng latLng) {
                        googleMap.clear();

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        markerOptions.position(latLng);
                        googleMap.addMarker(markerOptions);

                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latLng,
                                10,
                                0,
                                0
                        ));

                        googleMap.animateCamera(cameraUpdate, null);
                    }
                });
            }
        });



        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}