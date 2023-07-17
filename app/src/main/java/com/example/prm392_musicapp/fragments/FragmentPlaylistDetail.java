package com.example.prm392_musicapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.adapter.ChoosePlaylistAdapter;
import com.example.prm392_musicapp.adapter.PlaylistMusicAdapter;
import com.example.prm392_musicapp.models.Thumbnails;
import com.example.prm392_musicapp.models.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPlaylistDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPlaylistDetail extends Fragment {
    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase db;
    String id;
    String videoId;
    String title;
    String channelTitle;
    String thumbnail;
    private TextView tv_title;
    private Thumbnails thumbnails;
    private TextView tv_channel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPlaylistDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPlaylistDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPlaylistDetail newInstance(String param1, String param2) {
        FragmentPlaylistDetail fragment = new FragmentPlaylistDetail();
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

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sharedPreferencesPlaylistId = getActivity().getSharedPreferences("PlaylistID", Context.MODE_PRIVATE);
        int plid = sharedPreferencesPlaylistId.getInt("PLid", 0);
        List<Video> dataList = new ArrayList<>();
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity(), "ProjectDB", null, 1);
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT music.PLMid, music.PLMvideoId, music.PLMtitle, music.PLMchannelTitle, music.PLMthumbnails\n" +
                "FROM PlaylistMusic AS music\n" +
                "JOIN PLaylistMus AS PLM ON music.PLMid = PLM.PLMid\n" +
                "JOIN Playlists AS PL ON PL.PLid = PLM.PLid\n" +
                "WHERE PL.PLid = ?", new String[]{String.valueOf(plid)});
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex("PLMid"));
            videoId = cursor.getString(cursor.getColumnIndex("music.PLMvideoId"));
            title = cursor.getString(cursor.getColumnIndex("music.PLMtitle"));
            thumbnail = cursor.getString(cursor.getColumnIndex("music.PLMthumbnails"));
            channelTitle = cursor.getString(cursor.getColumnIndex("music.PLMchannelTitle"));
            Video data = new Video(videoId, title, thumbnail, channelTitle);
            dataList.add(data);
        }
        cursor.close();
        View view = inflater.inflate(R.layout.fragment_playlist_detail, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rec_playlist_detail);
        PlaylistMusicAdapter adapter = new PlaylistMusicAdapter(dataList, sharedPreferencesPlaylistId);

        SharedPreferences sharedPreferencesId = getActivity().getSharedPreferences("IdSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesIdEdit = sharedPreferencesId.edit();
        adapter.setOnItemClickListener(new PlaylistMusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String itemId) {
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
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}