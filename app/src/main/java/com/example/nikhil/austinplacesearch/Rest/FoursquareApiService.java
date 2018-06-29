package com.example.nikhil.austinplacesearch.Rest;

import com.example.nikhil.austinplacesearch.Model.SearchResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/*
* Service to call endpoints of API
* */

public interface FoursquareApiService {

   @GET("venues/search")
   Call<SearchResult> getPlaces(@QueryMap Map<String, String> options);
}
