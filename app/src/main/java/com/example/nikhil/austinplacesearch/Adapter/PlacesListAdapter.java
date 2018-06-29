package com.example.nikhil.austinplacesearch.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.austinplacesearch.Interface.PlacesListAdapterActionsInterface;
import com.example.nikhil.austinplacesearch.Model.Place;
import com.example.nikhil.austinplacesearch.Preference.SharedPreferences;
import com.example.nikhil.austinplacesearch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
* Adapter to load recycler view
* */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    Context context;
    int resource;
    ArrayList<Place> placeList;
    ArrayList<Place> favoritePlaceList;
    SharedPreferences sharedPreferences;
    PlacesListAdapterActionsInterface placesListAdapterActionsInterface;

    public PlacesListAdapter(Context context, int resource, ArrayList<Place> placeList, PlacesListAdapterActionsInterface placesListAdapterActionsInterface) {
        this.context = context;
        this.resource = resource;
        this.placeList = placeList;
        this.placesListAdapterActionsInterface = placesListAdapterActionsInterface;
        sharedPreferences = new SharedPreferences(context);
        favoritePlaceList = new ArrayList<>();
        Log.d("PlaceList", String.valueOf(this.placeList.size()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPlaceName, getTextViewPlaceCategory, textViewPlaceDistance;
        ImageView imageViewIcon, imageViewFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewPlaceDistance = (TextView) itemView.findViewById(R.id.textViewMainActivityPlacesListDistance);
            textViewPlaceName = (TextView) itemView.findViewById(R.id.textViewMainActivityPlacesListName);
            getTextViewPlaceCategory = (TextView) itemView.findViewById(R.id.textViewMainActivityPlacesListCategory);
            imageViewFavorite = (ImageView) itemView.findViewById(R.id.imageViewMainActivityPlacesListFavorite);
            imageViewIcon = (ImageView) itemView.findViewById(R.id.imageViewMainActivityPlacesListIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        favoritePlaceList = sharedPreferences.getFavorites();
        Log.d("Shared Preference",String.valueOf(favoritePlaceList.size()));
        final Place place = placeList.get(position);
        String placeIconUrl = place.getPlaceCategory().get(0).getPlaceIcon().getIconUrl();

        holder.textViewPlaceName.setText(place.getName());
        holder.textViewPlaceDistance.setText("Distance: "+place.getLocation().getDistance()+"m");
        holder.getTextViewPlaceCategory.setText(place.getPlaceCategory().get(0).getPlaceType());

        if(placeIconUrl != null && placeIconUrl != ""){
            Picasso.get()
                    .load(placeIconUrl)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(holder.imageViewIcon);
        } else {
            Picasso.get()
                    .load(android.R.drawable.ic_menu_report_image)
                    .into(holder.imageViewIcon);
        }

        if(favoritePlaceList.contains(place)){
            holder.imageViewFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            holder.imageViewFavorite.setTag(context.getResources().getString(R.string.favorite));
        } else {
            holder.imageViewFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            holder.imageViewFavorite.setTag(context.getResources().getString(R.string.not_favorite));
        }

        holder.imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag() == context.getResources().getString(R.string.favorite)) {
                    ((ImageView) view).setImageResource(android.R.drawable.btn_star_big_off);
                    view.setTag(context.getResources().getString(R.string.not_favorite));
                    removeFavorite(place);
                } else if (view.getTag() == context.getResources().getString(R.string.not_favorite)) {
                    ((ImageView) view).setImageResource(android.R.drawable.btn_star_big_on);
                    view.setTag(context.getResources().getString(R.string.favorite));
                    setFavorite(place);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    private void setFavorite(Place place) {
        sharedPreferences.addFavorite(place);
    }

    private void removeFavorite(Place place) {
        sharedPreferences.removeFavorite(place);
    }

    //Function to handle item click from recycler view
    private void itemClicked(int pos){
        placesListAdapterActionsInterface.openDetails(placeList.get(pos));
    }
}
