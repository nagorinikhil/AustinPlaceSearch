package com.example.nikhil.austinplacesearch.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
* Model to store information related to place obtainecd from API
* */

public class Place implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("contact")
    @Expose
    private PlaceContact placeContact;

    @SerializedName("location")
    @Expose
    private PlaceLocation location;

    @SerializedName("categories")
    @Expose
    private List<PlaceCategory> placeCategory;

    @SerializedName("url")
    @Expose
    private String url;

    protected Place(Parcel in) {
        id = in.readString();
        name = in.readString();
        url = in.readString();
        location = in.readParcelable(PlaceLocation.class.getClassLoader());
        placeCategory = in.createTypedArrayList(PlaceCategory.CREATOR);
        placeContact = in.readParcelable(PlaceContact.class.getClassLoader());
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlaceContact getPlaceContact() {
        return placeContact;
    }

    public void setPlaceContact(PlaceContact placeContact) {
        this.placeContact = placeContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PlaceLocation getLocation() {
        return location;
    }

    public void setLocation(PlaceLocation location) {
        this.location = location;
    }

    public List<PlaceCategory> getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(List<PlaceCategory> placeCategory) {
        this.placeCategory = placeCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeParcelable(location, flags);
        dest.writeTypedList(placeCategory);
        dest.writeParcelable(placeContact, flags);
    }

    @Override
    public boolean equals(Object obj) {
        Place place = (Place) obj;
        return id.equals(place.id) && name.equals(place.name);
    }
}
