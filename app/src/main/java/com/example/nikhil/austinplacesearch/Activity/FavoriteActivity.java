package com.example.nikhil.austinplacesearch.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.nikhil.austinplacesearch.Adapter.FavoriteListAdapter;
import com.example.nikhil.austinplacesearch.Adapter.PlacesListAdapter;
import com.example.nikhil.austinplacesearch.Interface.PlacesListAdapterActionsInterface;
import com.example.nikhil.austinplacesearch.Model.Place;
import com.example.nikhil.austinplacesearch.Preference.SharedPreferences;
import com.example.nikhil.austinplacesearch.R;

import java.util.ArrayList;

/*
* Display Favorites
* */

public class FavoriteActivity extends AppCompatActivity implements PlacesListAdapterActionsInterface{

    Toolbar myToolbar;
    RecyclerView recyclerView;
    FavoriteListAdapter favoriteListAdapter;
    ArrayList<Place> placeArrayList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        myToolbar = (Toolbar) findViewById(R.id.toolbarFavoriteActivity);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFavoriteActivity);
        sharedPreferences = new SharedPreferences(this);

        placeArrayList = new ArrayList<>();

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Favorites");

    }

    @Override
    public void openDetails(Place place) {
        Intent intent = new Intent(FavoriteActivity.this, PlaceDetailActivity.class);
        intent.putExtra("PlaceDetails",place);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        placeArrayList = sharedPreferences.getFavorites();
        favoriteListAdapter = new FavoriteListAdapter(FavoriteActivity.this,R.layout.activity_main_places_list_item,placeArrayList, FavoriteActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
        recyclerView.setAdapter(favoriteListAdapter);
        favoriteListAdapter.notifyDataSetChanged();
    }
}
