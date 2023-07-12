package com.example.prm392_musicapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleItemDetail {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("items")
    @Expose
    private List<SingleItem> singleItems;

    public List<SingleItem> getSingleItems() {
        return singleItems;
    }

    public void setSingleItems(List<SingleItem> singleItems) {
        this.singleItems = singleItems;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
