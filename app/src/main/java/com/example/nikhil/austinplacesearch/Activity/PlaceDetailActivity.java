package com.example.nikhil.austinplacesearch.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nikhil.austinplacesearch.Model.Place;
import com.example.nikhil.austinplacesearch.Preference.SharedPreferences;
import com.example.nikhil.austinplacesearch.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
* Activity to display details of the place selected from MainActivity or Map Activity
* */

public class PlaceDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView textViewAddress, textViewPhone, textViewTwitter, textViewFacebook, textViewUrl, textViewInstagram, textViewName;
    ImageView imageViewFavorite, imageViewCollapsingToolbar;
    ProgressBar progressBar;
    AppBarLayout appBarLayout;

    String address, facebookName, twitterName, phoneNumber, instagramName, url, name;
    SharedPreferences sharedPreferences;
    Place place;

    ArrayList<Place> arrayListfavoritePlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        textViewAddress = (TextView) findViewById(R.id.textViewPlaceDetailAddress);
        textViewPhone = (TextView) findViewById(R.id.textViewPlaceDetailPhoneNumber);
        textViewTwitter = (TextView) findViewById(R.id.textViewPlaceDetailTwitter);
        textViewFacebook = (TextView) findViewById(R.id.textViewPlaceDetailFacebookName);
        textViewUrl = (TextView) findViewById(R.id.textViewPlaceDetailUrl);
        textViewInstagram = (TextView) findViewById(R.id.textViewPlaceDetailInstagram);
        textViewName = (TextView) findViewById(R.id.textViewPlaceDetailName);
        imageViewFavorite = (ImageView) findViewById(R.id.imageViewPlaceDetailFavorite);
        imageViewCollapsingToolbar = (ImageView) findViewById(R.id.imageViewCollapsingToolbarPlaceDetails);
        progressBar = (ProgressBar) findViewById(R.id.progressBarCollapsingToolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayoutPlaceDetails);

        progressBar.setVisibility(View.VISIBLE);

        sharedPreferences = new SharedPreferences(this);
        arrayListfavoritePlaces = new ArrayList<>();
        arrayListfavoritePlaces = sharedPreferences.getFavorites();

        Bundle data = getIntent().getExtras();
        place = (Place) data.getParcelable("PlaceDetails");

        Log.d("Place:",String.valueOf(arrayListfavoritePlaces.size()));

        setSupportActionBar((Toolbar) findViewById(R.id.toolbarPlaceDetail));
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayoutPlaceDetails);
        collapsingToolbarLayout.setTitle(place.getName());

        String staticMapUrl = getStaticMapUrl(place);
        Log.d("URL", staticMapUrl);
        Picasso.get()
                .load(staticMapUrl)
                .error(android.R.drawable.stat_notify_error)
                .into(imageViewCollapsingToolbar, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        setUiDetails(place);
        imageViewFavorite.setOnClickListener(favoriteImageClickListener);
        textViewUrl.setOnClickListener(urlClickListener);
    }

    View.OnClickListener urlClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String url = ((TextView) view).getText().toString();
            if(url!=""){
                Uri website = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, website);
                startActivity(intent);
            }
        }
    };

    public String getStaticMapUrl(Place place){
        String marker1 = "markers=color:red|30.2672,-97.7431";
        String marker2 = "markers=color:blue|"+String.valueOf(place.getLocation().getLat()) + ","+ String.valueOf(place.getLocation().getLng());
        String scale = "scale=1";
        String format = "format=jpg";
        String key = "key=" + getResources().getString(R.string.google_map_api_key);
        String size = "size=400x400";

        String staticMapUrl = getResources().getString(R.string.base_url_google_static_map)+
                        "?"+ size + "&" + scale + "&" + format + "&" + marker1 + "&" + marker2 + "&" + key;

        return staticMapUrl;

    }

    View.OnClickListener favoriteImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag() == getResources().getString(R.string.favorite)) {
                ((ImageView) view).setImageResource(android.R.drawable.btn_star_big_off);
                view.setTag(getResources().getString(R.string.not_favorite));
                sharedPreferences.removeFavorite(place);

            } else if (view.getTag() == getResources().getString(R.string.not_favorite)) {
                ((ImageView) view).setImageResource(android.R.drawable.btn_star_big_on);
                view.setTag(getResources().getString(R.string.favorite));
                sharedPreferences.addFavorite(place);
            }
        }
    };

    public void setUiDetails(Place place){

        address = place.getLocation().getFullAddress();
        phoneNumber = place.getPlaceContact().getPhoneNumber();
        twitterName = place.getPlaceContact().getTwitter();
        facebookName = place.getPlaceContact().getFacebookName();
        instagramName = place.getPlaceContact().getInstagram();
        url = place.getUrl();
        name = place.getName();

        textViewName.setText(name);

        if(arrayListfavoritePlaces.contains(place)){
            imageViewFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            imageViewFavorite.setTag(getResources().getString(R.string.favorite));
        }
        else {
            imageViewFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            imageViewFavorite.setTag(getResources().getString(R.string.not_favorite));
        }

        if(phoneNumber!=null)
            textViewPhone.setText("Phone: "+phoneNumber);
        else
            textViewPhone.setText("");

        if(address!=null)
            textViewAddress.setText(address);
        else
            textViewAddress.setText("");

        if(twitterName!=null)
            textViewTwitter.setText("Twitter: "+twitterName);
        else
            textViewTwitter.setText("");

        if(facebookName!=null)
            textViewFacebook.setText("Facebook: "+facebookName);
        else
            textViewFacebook.setText("");

        if(instagramName!=null)
            textViewInstagram.setText("Instagram: "+instagramName);
        else
            textViewInstagram.setText("");

        if(url!=null)
            textViewUrl.setText(url);
        else
            textViewUrl.setText("");
    }
}
