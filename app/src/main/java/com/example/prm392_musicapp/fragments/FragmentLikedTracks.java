package com.example.prm392_musicapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.adapter.LikedMusicAdapter;
import com.example.prm392_musicapp.adapter.RecommendAdapter;
import com.example.prm392_musicapp.models.Thumbnails;
import com.example.prm392_musicapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLikedTracks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLikedTracks extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentLikedTracks fragmentLikedTracks;
    private TextView tv_title;
    private Thumbnails thumbnails;
    private TextView tv_channel;
    String id;
    String videoId;
    String title;
    String channelTitle;
    String thumbnail;
    ImageView btnBack;

    FragmentLibrary fragmentLibrary;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase db;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLikedTracks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLikedTracks.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLikedTracks newInstance(String param1, String param2) {
        FragmentLikedTracks fragment = new FragmentLikedTracks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.liked_tracks_page, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentLibrary = new FragmentLibrary();
        ((ImageView) view.findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fr_container, fragmentLibrary, "");
                fragmentTransaction.commit();
            }
        });

        List<Video> dataList = new ArrayList<>();
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity(), "ProjectDB", null, 1);
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LikedTracks", null);
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex("LTid"));
            videoId = cursor.getString(cursor.getColumnIndex("videoId"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            thumbnail = cursor.getString(cursor.getColumnIndex("thumbnails"));
            channelTitle = cursor.getString(cursor.getColumnIndex("channelTitle"));
            Video data = new Video(videoId, title, thumbnail, channelTitle);
            dataList.add(data);
        }
        cursor.close();
        SharedPreferences sharedPreferencesId = getActivity().getSharedPreferences("IdSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesIdEdit = sharedPreferencesId.edit();
        if (dataList.size() > 0) {
            // Hiển thị dữ liệu trong RecyclerView
            RecyclerView recyclerView = view.findViewById(R.id.rec_liked_track);
            LikedMusicAdapter adapter = new LikedMusicAdapter(dataList);
            //click de phat
            adapter.setOnItemClickListener(new LikedMusicAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String videoId) {
                    Log.i("run1", "onItemClick" + videoId);
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    intent.putExtra("itemId", videoId);
                    //lấy giá trị check có phải click vào player bar hay không
                    SharedPreferences sharedPrefPlayerBar = getActivity().getSharedPreferences("PlayerBarSharePref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefPlayerBarEdit = sharedPrefPlayerBar.edit();
                    SharedPreferences sharedPrefSuffle = getActivity().getSharedPreferences("SuffleSharePref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefSuffleEdit = sharedPrefSuffle.edit();
                    SharedPreferences sharedPreferencesSkip = getActivity().getSharedPreferences("SkipSharePref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPreferencesSkipEdit = sharedPreferencesSkip.edit();
                    sharedPrefPlayerBarEdit.putBoolean("isClicked", false);
                    sharedPrefPlayerBarEdit.apply();

                    boolean isSuffle = sharedPrefSuffle.getBoolean("isSuffle", false);
                    boolean isSkip = sharedPreferencesSkip.getBoolean("isSkip", false);
                    //lưu currentId và prevId trên share preferences
                    String currentID = sharedPreferencesId.getString("currentId", null);
                    String prevID = sharedPreferencesId.getString("prevId", null);
                    if (currentID == null) {
                        sharedPreferencesIdEdit.putString("currentId", videoId);
                        sharedPreferencesIdEdit.putString("prevId", videoId);
                    } else if (currentID != null && prevID != null) {
                        if (!currentID.equals(videoId) && !isSuffle) {
                            sharedPreferencesIdEdit.putString("prevId", currentID);
                            sharedPreferencesIdEdit.putString("currentId", videoId);
                        } else if (currentID.equals(videoId) && !isSuffle) {
                            sharedPreferencesIdEdit.putString("prevId", videoId);
                        } else if (isSuffle) {
                            if (!videoId.equals(currentID)) {
                                sharedPreferencesIdEdit.putString("prevId", currentID);
                                sharedPreferencesIdEdit.putString("currentId", videoId);
                                sharedPrefSuffleEdit.putBoolean("isSuffle", false);
                            } else {
                                sharedPrefSuffleEdit.putBoolean("isSuffle", false);
                            }
                            sharedPrefSuffleEdit.apply();
                        } else if (isSkip) {
                            if (!videoId.equals(currentID)) {
                                sharedPreferencesIdEdit.putString("prevId", currentID);
                                sharedPreferencesIdEdit.putString("currentId", videoId);
                                sharedPreferencesSkipEdit.putBoolean("isSkip", false);
                            } else {
                                sharedPreferencesSkipEdit.putBoolean("isSkip", false);
                            }
                            sharedPreferencesSkipEdit.apply();
                        }
                    }
                    sharedPreferencesIdEdit.apply();
                    startActivity(intent);
                }
            });
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            ((ConstraintLayout) view.findViewById(R.id.layout_liked_track)).setVisibility(View.VISIBLE);
            ((ConstraintLayout) view.findViewById(R.id.layout_noLikeTrack)).setVisibility(View.INVISIBLE);
        } else {
            ((ConstraintLayout) view.findViewById(R.id.layout_liked_track)).setVisibility(View.INVISIBLE);
            ((ConstraintLayout) view.findViewById(R.id.layout_noLikeTrack)).setVisibility(View.VISIBLE);
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mode", Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean("dark", false);
        btnBack = view.findViewById(R.id.btn_back);
        if (darkMode) {
            btnBack.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_light);
        } else {
            btnBack.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_dark);
        }
        return view;
    }

    public void setSQLiteOpenHelper(MySQLiteOpenHelper mySQLiteOpenHelper) {
        this.mySQLiteOpenHelper = mySQLiteOpenHelper;
    }

}