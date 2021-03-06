package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.ui.gallery;

import static android.content.Context.LOCATION_SERVICE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.SupportMapFragment;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.MainActivity;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.R;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.SaveLocationActivity;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.abstracts.UserLocationDatabase;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.adapters.UserLocationAdapter;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.databinding.FragmentGalleryBinding;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.listeners.UserLocationTouchListener;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;

import java.util.List;

public class GalleryFragment extends Fragment {

    public static final String MARKER_EDIT_MODE = "MARKER_EDIT_MODE";
    private FragmentGalleryBinding binding;
    private RecyclerView recyclerView;
    private GalleryViewModel placesViewModel;
    private UserLocationAdapter userLocationAdapter;
    private List<UserLocation> userLocationList;
    private UserLocationDatabase appDB;
    private SearchView searchView;
    private Location currrentLocation;
    private LocationManager mLocationManager;

    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        GalleryViewModel galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);
        placesViewModel = new GalleryViewModel(getContext());
//        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel::class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = (RecyclerView) root.findViewById(R.id.userLocationsRecyclerView);
        appDB = UserLocationDatabase.getInstance(getContext());
        userLocationList = appDB.userLocationDao().getAllLocations();

        userLocationAdapter = new UserLocationAdapter(getContext(), userLocationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(userLocationAdapter);
        searchView = (SearchView) root.findViewById(R.id.sVSearchBar);

        UserLocationTouchListener touchListener = new UserLocationTouchListener(getActivity(), recyclerView);
        touchListener.setClickable(new UserLocationTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.delete_task,R.id.edit_task)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new UserLocationTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        UserLocation userLocation = userLocationList.get(position);

                        switch (viewID){
                            case R.id.delete_task:
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Do you want to save this location ? ")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Continue with delete operation
                                                appDB.userLocationDao().deleteUserLocation(userLocation);
                                                refreshData();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .show();
                                break;
                            case R.id.edit_task:
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra(MARKER_EDIT_MODE, userLocation);
                                startActivity(intent);
                                break;

                        }
                    }
                });

        recyclerView.addOnItemTouchListener(touchListener);

        Observer<List<UserLocation>> userLocationUpdateObserver = new Observer<List<UserLocation>>() {
            @Override
            public void onChanged(List<UserLocation> userLocations) {
                userLocationAdapter.setuserLocationList(userLocationList);
            }
        };

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<UserLocation> userLocations = appDB.userLocationDao().getFilteredLocations(s);
                userLocationAdapter.setuserLocationList(userLocations);
                return false;
            }
        });

        return root;
    }

    private void refreshData() {
        List<UserLocation> userLocations = appDB.userLocationDao().getAllLocations();
        userLocationAdapter.setuserLocationList(userLocations);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            currrentLocation = location;
            UserLocation.setCurrentLocation(location);
        }
    };
}