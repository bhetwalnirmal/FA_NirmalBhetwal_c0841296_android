package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.MainActivity;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.R;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        GalleryViewModel galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
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
                        switch (viewID){
                            case R.id.delete_task:

                                break;
                            case R.id.edit_task:
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                UserLocation userLocation = userLocationList.get(position);
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}