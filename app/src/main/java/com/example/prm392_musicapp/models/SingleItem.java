package com.example.prm392_musicapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleItem {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public static class SingleItemDetail {
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
}
