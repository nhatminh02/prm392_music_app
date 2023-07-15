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
        String sqlLT = "Create Table LikedTracks " +
                "(LTid INTEGER PRIMARY KEY, videoId TEXT, title TEXT, thumbnails TEXT, channelTitle TEXT)";
        db.execSQL(sqlLT);

        String sqlRec = "Create Table Recently " +
                "(recId INTEGER PRIMARY KEY, videoId TEXT, title TEXT, thumbnails TEXT, channelTitle TEXT)";
        db.execSQL(sqlRec);

        String sqlRecommend = "Create Table Recommends " +
                "(recommendId INTEGER PRIMARY KEY, videoId TEXT, title TEXT, thumbnails TEXT, channelTitle TEXT)";
        db.execSQL(sqlRecommend);

        String sql2 = "Create Table Playlists" +
                "(PLid INTEGER PRIMARY KEY, PLName TEXT)";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //nang cap version
        if (newVersion > oldVersion) {
            db.execSQL("Drop table if exists LikedTracks");
            db.execSQL("Drop table if exists Recently");
            db.execSQL("Drop table if exists Reccomends");
            db.execSQL("Drop table if exists Playlists");
            onCreate(db);
        }
    }

}
