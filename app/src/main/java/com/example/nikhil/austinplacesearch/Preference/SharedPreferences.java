package com.example.nikhil.austinplacesearch.Preference;

import android.content.Context;

import com.example.nikhil.austinplacesearch.Model.Place;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* Class to keep track of places marked as favorite
* */

public class SharedPreferences {
    private static final String USER_PREFS = "FAV_PLACE";
    private android.content.SharedPreferences.Editor editor = null;
    private android.content.SharedPreferences preferences = null;

    public SharedPreferences(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(
                USER_PREFS, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void addFavorite(Place place){
        List<Place> favoritePlaces = getFavorites();
        if (favoritePlaces == null)
            favoritePlaces = new ArrayList<Place>();
        favoritePlaces.add(place);
        saveFavorites(favoritePlaces);
    }

    public void removeFavorite(Place place){
        ArrayList<Place> favorites = getFavorites();
        if (favorites != null) {
            favorites.remove(place);
            saveFavorites(favorites);
        }
    }

    public void saveFavorites(List<Place> favoritePlaces) {
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favoritePlaces);
        editor.putString(USER_PREFS, jsonFavorites);
        editor.commit();
    }

    public ArrayList<Place> getFavorites(){
        List<Place> favPlaceList;
        if(preferences.contains(USER_PREFS)){
            String jsonFavorites = preferences.getString(USER_PREFS, null);
            Gson gson = new Gson();
            Place[] favoritePlace = gson.fromJson(jsonFavorites, Place[].class);
            favPlaceList = Arrays.asList(favoritePlace);
            favPlaceList = new ArrayList<Place>(favPlaceList);
            return (ArrayList<Place>) favPlaceList;
        } else {
            return new ArrayList<Place>();
        }
    }

}
