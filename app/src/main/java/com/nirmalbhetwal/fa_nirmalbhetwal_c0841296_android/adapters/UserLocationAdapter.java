package com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.R;
import com.nirmalbhetwal.fa_nirmalbhetwal_c0841296_android.models.UserLocation;

import java.util.ArrayList;
import java.util.List;

public class UserLocationAdapter extends RecyclerView.Adapter<UserLocationAdapter.ViewHolder> {
    private Context context;
    List<UserLocation> userLocationList;

    public UserLocationAdapter(Context context, List<UserLocation> userLocationList) {
        this.context = context;
        this.userLocationList = userLocationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        // use the inflater to inflate from the dashboard_row xml layout file
        View listItem = inflater.inflate(R.layout.places_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserLocation userLocation = userLocationList.get(position);

        holder.title.setText(userLocation.getTitle());
        holder.subltile.setText(userLocation.getTitle());
        holder.description.setText(userLocation.getDescription());

        if (userLocation.isFavourite()) {
            holder.favourite.setVisibility(View.VISIBLE);
        } else {
            holder.favourite.setVisibility(View.GONE);
        }

        if (userLocation.isHasVisitedTheLocation()) {
            holder.hasVisited.setVisibility(View.VISIBLE);
        } else {
            holder.hasVisited.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.userLocationList.size();
    }

    public void filterData(String s) {
        List<UserLocation> UserLocations = new ArrayList<>();

        for (UserLocation UserLocation : userLocationList) {

        }

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subltile, description;
        Button favourite, hasVisited;

        public ViewHolder (View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.tvUserLocationTitle);
            this.subltile = (TextView) itemView.findViewById(R.id.tvUserLocationSubTitle);
            this.description = (TextView) itemView.findViewById(R.id.tvUserLocationDescription);
            this.favourite = (Button) itemView.findViewById(R.id.btnIsFavourite);
            this.hasVisited = (Button) itemView.findViewById(R.id.btnHasVisited);
        }
    }

    public void setuserLocationList (List<UserLocation> userLocationList) {
        this.userLocationList = userLocationList;
        notifyDataSetChanged();
    }
}
