package com.example.nikhil.austinplacesearch.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
* Model to store image url obtained from API
* */

public class PlaceIcon implements Parcelable{

    @SerializedName("prefix")
    @Expose
    private String prefix;

    @SerializedName("Suffix")
    @Expose
    private String suffix;

    protected PlaceIcon(Parcel in) {
        prefix = in.readString();
        suffix = in.readString();
    }

    public static final Creator<PlaceIcon> CREATOR = new Creator<PlaceIcon>() {
        @Override
        public PlaceIcon createFromParcel(Parcel in) {
            return new PlaceIcon(in);
        }

        @Override
        public PlaceIcon[] newArray(int size) {
            return new PlaceIcon[size];
        }
    };

    public String getIconUrl(){
        return prefix + suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prefix);
        dest.writeString(suffix);
    }
}
