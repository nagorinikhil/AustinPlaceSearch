package com.example.nikhil.austinplacesearch.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.nikhil.austinplacesearch.Adapter.PlacesListAdapter;
import com.example.nikhil.austinplacesearch.Interface.PlacesListAdapterActionsInterface;
import com.example.nikhil.austinplacesearch.Model.Place;
import com.example.nikhil.austinplacesearch.Model.ResponseList;
import com.example.nikhil.austinplacesearch.Model.SearchResult;
import com.example.nikhil.austinplacesearch.R;
import com.example.nikhil.austinplacesearch.Rest.FoursquareApiClient;
import com.example.nikhil.austinplacesearch.Rest.FoursquareApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PlacesListAdapterActionsInterface{

    EditText editTextSearchPlace;
    Button buttonSearch;
    FloatingActionButton floatingActionButtonMaps;
    Toolbar myToolbar;
    RecyclerView recyclerViewPlacesList;
    ProgressBar progressBar;

    PlacesListAdapter placesListAdapter;
    ArrayList<Place> placeArrayList;
    FoursquareApiService foursquareApiService;
    Call<SearchResult> call;
    Map<String, String> queryMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSearch = (Button) findViewById(R.id.buttonPlaceSearch);
        editTextSearchPlace = (EditText) findViewById(R.id.editTextSearchPlace);
        recyclerViewPlacesList = (RecyclerView) findViewById(R.id.recyclerViewMainActivityPlaces);
        floatingActionButtonMaps = (FloatingActionButton) findViewById(R.id.floatingActionButtonOpenMaps);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMainActivity);
        myToolbar = (Toolbar) findViewById(R.id.toolbarMainActivity);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        placeArrayList = new ArrayList<>();
        foursquareApiService = FoursquareApiClient.getClient().create(FoursquareApiService.class);

        progressBar.setVisibility(View.GONE);
        makeQuery();

        buttonSearch.setOnClickListener(buttonSearchListener);
        floatingActionButtonMaps.setOnClickListener(floatingActionButtonListener);
    }

    View.OnClickListener floatingActionButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putParcelableArrayListExtra("PlacesList", placeArrayList);
            startActivity(intent);
        }
    };

    View.OnClickListener buttonSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            if(!TextUtils.isEmpty(editTextSearchPlace.getText().toString())){
                queryMap.put("query", String.valueOf(editTextSearchPlace.getText()));
                loadPlaces(queryMap);
            }
            else
                editTextSearchPlace.setError("Enter Place to search");
        }
    };

    public void makeQuery(){
        queryMap = new HashMap<String, String>();
        queryMap.put("client_id", getResources().getString(R.string.foursquare_api_client_id));
        queryMap.put("client_secret", getResources().getString(R.string.foursquare_api_client_secret));
        queryMap.put("near", "Austin,+TX");
        queryMap.put("ll","30.2672,-97.7431");
        queryMap.put("v", "20170801");
        queryMap.put("limit","20");
    }

    /*
    * make API call and load places into recycler view
    * */
    public void loadPlaces(Map<String, String> queryMap){
        showProgressBar();

        call = foursquareApiService.getPlaces(queryMap);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                hideProgressBar();

                ResponseList rl = response.body().getResponseList();
                placeArrayList = (ArrayList<Place>) rl.getPlaceList();
                placesListAdapter = new PlacesListAdapter(MainActivity.this,R.layout.activity_main_places_list_item,placeArrayList, MainActivity.this);
                recyclerViewPlacesList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerViewPlacesList.setAdapter(placesListAdapter);
                placesListAdapter.notifyDataSetChanged();

                Log.d("Array Size", String.valueOf( placeArrayList.size()));
                Log.d("Success",String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.d("Fail", "Fail");
            }
        });
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void openDetails(Place place) {
        Intent intent = new Intent(MainActivity.this, PlaceDetailActivity.class);
        intent.putExtra("PlaceDetails",place);
        startActivity(intent);
    }

    /*
    * To handle configuration changes and loading the values again automatically to maintain consistency
    * */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("Search",editTextSearchPlace.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(!savedInstanceState.getString("Search").equals("")) {
            editTextSearchPlace.setText(savedInstanceState.getString("Search"));
            queryMap.put("query", savedInstanceState.getString("Search"));
            loadPlaces(queryMap);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(placesListAdapter!=null)
            placesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
