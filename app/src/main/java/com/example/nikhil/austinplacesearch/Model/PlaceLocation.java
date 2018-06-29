package com.example.nikhil.austinplacesearch.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
* Model to store location related values obtained from api
* */

public class PlaceLocation implements Parcelable{

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lng")
    @Expose
    private double lng;

    @SerializedName("distance")
    @Expose
    private int distance;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("cc")
    @Expose
    private String country;

    @SerializedName("postalCode")
    @Expose
    private int postalCode;

    protected PlaceLocation(Parcel in) {
        address = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        distance = in.readInt();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        postalCode = in.readInt();
    }

    public static final Creator<PlaceLocation> CREATOR = new Creator<PlaceLocation>() {
        @Override
        public PlaceLocation createFromParcel(Parcel in) {
            return new PlaceLocation(in);
        }

        @Override
        public PlaceLocation[] newArray(int size) {
            return new PlaceLocation[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getFullAddress(){
        return address + "\n" + city + " " + state + "\n" + country + " " + postalCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeInt(distance);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeInt(postalCode);
    }
}
