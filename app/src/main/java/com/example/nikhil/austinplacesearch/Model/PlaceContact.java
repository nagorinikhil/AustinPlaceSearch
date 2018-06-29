package com.example.nikhil.austinplacesearch.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
* Model to store all the contact related information obtained from API
* */

public class PlaceContact implements Parcelable{

    @SerializedName("formattedPhone")
    @Expose
    private String phoneNumber;

    @SerializedName("twitter")
    @Expose
    private String twitter;

    @SerializedName("instagram")
    @Expose
    private String instagram;

    @SerializedName("facebookName")
    @Expose
    private String facebookName;

    protected PlaceContact(Parcel in) {
        phoneNumber = in.readString();
        twitter = in.readString();
        instagram = in.readString();
        facebookName = in.readString();
    }

    public static final Creator<PlaceContact> CREATOR = new Creator<PlaceContact>() {
        @Override
        public PlaceContact createFromParcel(Parcel in) {
            return new PlaceContact(in);
        }

        @Override
        public PlaceContact[] newArray(int size) {
            return new PlaceContact[size];
        }
    };

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebookName() {
        return facebookName;
    }

    public void setFacebookName(String facebookName) {
        this.facebookName = facebookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phoneNumber);
        dest.writeString(twitter);
        dest.writeString(instagram);
        dest.writeString(facebookName);
    }
}
