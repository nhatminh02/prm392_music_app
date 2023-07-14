package com.example.prm392_musicapp.models;

public class Video {
    private String videoId;
    private String title;
    private String thumbnails;
    private String channelTitle;

    public Video(String videoId, String title, String thumbnails, String channelTitle) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnails = thumbnails;
        this.channelTitle = channelTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
