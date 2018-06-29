package com.example.nikhil.austinplacesearch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {

    @SerializedName("response")
    @Expose
    private ResponseList responseList;

    public ResponseList getResponseList() {
        return responseList;
    }

    public void setResponseList(ResponseList responseList) {
        this.responseList = responseList;
    }
}
