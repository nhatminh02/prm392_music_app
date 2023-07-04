package com.example.prm392_musicapp.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SearchItemDetails {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("items")
    @Expose
    private List<SearchItem> searchItems;

    public List<SearchItem> getItems() {
        return searchItems;
    }

    public void setItems(List<SearchItem> searchItems) {
        this.searchItems = searchItems;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}