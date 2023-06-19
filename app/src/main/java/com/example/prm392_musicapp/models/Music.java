package com.example.prm392_musicapp.models;

public class Music {
    private int thumbnail;
    private String musicName;
    private String singer;

    public Music(int thumbnail, String musicName, String singer) {
        this.thumbnail = thumbnail;
        this.musicName = musicName;
        this.singer = singer;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
