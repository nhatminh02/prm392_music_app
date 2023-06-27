package com.example.prm392_musicapp.LikeTrackDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.prm392_musicapp.models.Item;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table likeMusic (id integer PRIMARY KEY, channelId TEXT PRIMARY KEY, videoId TEXT PRIMARY KEY, title TEXT, thumbnail TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            //TODO: drop old table, re-create new table
        }
    }

}
