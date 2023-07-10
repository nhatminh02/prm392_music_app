package com.example.prm392_musicapp.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version); //tao ra db
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //tao cac bang
        String sql = "Create Table LikedTracks " +
                "(LTid INTEGER PRIMARY KEY, videoId TEXT, title TEXT, thumbnails TEXT, channelTitle TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //nang cap version
        if(newVersion > oldVersion){
            String sql1 = "Drop Table LikedTracks";
            db.execSQL(sql1);

            String sql2 = "Create Table LikedTracks " +
                    "(LTid INTEGER PRIMARY KEY, videoId TEXT, title TEXT, thumbnails TEXT, channelTitle TEXT)";
            db.execSQL(sql2);
        }
    }
}
