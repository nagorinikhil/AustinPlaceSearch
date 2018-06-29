package com.example.nikhil.austinplacesearch.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceCategory implements Parcelable{

    @SerializedName("name")
    @Expose
    private String placeType;

    @SerializedName("icon")
    @Expose
    private PlaceIcon placeIcon;

    protected PlaceCategory(Parcel in) {
        placeType = in.readString();
        placeIcon = in.readParcelable(PlaceIcon.class.getClassLoader());
    }

    public static final Creator<PlaceCategory> CREATOR = new Creator<PlaceCategory>() {
        @Override
        public PlaceCategory createFromParcel(Parcel in) {
            return new PlaceCategory(in);
        }

        @Override
        public PlaceCategory[] newArray(int size) {
            return new PlaceCategory[size];
        }
    };

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public PlaceIcon getPlaceIcon() {
        return placeIcon;
    }

    public void setPlaceIcon(PlaceIcon placeIcon) {
        this.placeIcon = placeIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeType);
        dest.writeParcelable(placeIcon, flags);
    }
}
