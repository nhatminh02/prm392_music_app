package com.example.prm392_musicapp.SQLite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.example.prm392_musicapp.models.Thumbnails;
import com.example.prm392_musicapp.models.Video;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version); //tao ra db
    }


    @Override
    public void onCreate(SQLiteDatabase db) { //tao cac bang
        String sql = "Create Table LikedTracks " +
                "(LTid INTEGER PRIMARY KEY, videoId TEXT, title TEXT, thumbnails TEXT, channelTitle TEXT)";
        String sql2 = "Create Table Playlists" +
                "(PLid INTEGER PRIMARY KEY, PLName TEXT)";
        String sql3 = "Create Table PlaylistMusic" +
                "(PLMid INTEGER PRIMARY KEY, PLMvideoId TEXT, PLMtitle TEXT, PLMthumbnails TEXT, PLMchannelTitle TEXT)";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //nang cap version
        if (newVersion > oldVersion) {
            String sql1 = "Drop Table LikedTracks";
            db.execSQL(sql1);

            String sql2 = "Create Table LikedTracks " +
                    "(LTid INTEGER PRIMARY KEY, videoId TEXT, title TEXT, thumbnails TEXT, channelTitle TEXT)";
            db.execSQL(sql2);
        }
    }

}
