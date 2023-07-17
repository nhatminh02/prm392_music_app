package com.example.prm392_musicapp.fragments;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.adapter.RecentlyPlayedAdapter;
import com.example.prm392_musicapp.adapter.RecommendAdapter;
import com.example.prm392_musicapp.adapter.SearchAdapter;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.SingleItem;
import com.example.prm392_musicapp.models.Video;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page, container, false);
        openHelper = new MySQLiteOpenHelper(getContext(), "ProjectDB", null, 1);
        db = openHelper.getReadableDatabase();

        //lay ra list recommends
        String sql = "select * from Recommends order by recommendId desc";
        List<Video> recommends = new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            String videoId = c.getString(1);
            String title = c.getString(2);
            String thumbnails = c.getString(3);
            String channelTitle = c.getString(4);
            recommends.add(new Video(videoId, title, thumbnails, channelTitle));
        }

        SharedPreferences sharedPreferencesId = getActivity().getSharedPreferences("IdSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesIdEdit = sharedPreferencesId.edit();
        RecommendAdapter adapterRecommend = new RecommendAdapter(recommends, getActivity());
        //click de phat
        adapterRecommend.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
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
        RecyclerView rec1 = view.findViewById(R.id.rec_reccomend);
        RecyclerView.LayoutManager layout_manager1 =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rec1.setLayoutManager(layout_manager1);
        rec1.setAdapter(adapterRecommend);

        //lay ra list recently
        sql = "select * from Recently order by recId desc";
        List<Video> recently = new ArrayList<>();
        c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            String videoId = c.getString(1);
            String title = c.getString(2);
            String thumbnails = c.getString(3);
            String channelTitle = c.getString(4);
            recently.add(new Video(videoId, title, thumbnails, channelTitle));
        }
        RecentlyPlayedAdapter adapterRecently = new RecentlyPlayedAdapter(recently, getActivity());
        //click de phat
        adapterRecently.setOnItemClickListener(new RecentlyPlayedAdapter.OnItemClickListener() {
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
                    if (!currentID.equals(videoId) && !isSuffle && !isSkip) {
                        sharedPreferencesIdEdit.putString("prevId", currentID);
                        sharedPreferencesIdEdit.putString("currentId", videoId);
                    } else if (currentID.equals(videoId) && !isSuffle && !isSkip) {
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
        RecyclerView rec2 = view.findViewById(R.id.rec_recently);
        RecyclerView.LayoutManager layout_manager2 =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rec2.setLayoutManager(layout_manager2);
        rec2.setAdapter(adapterRecently);

        return view;
    }
}