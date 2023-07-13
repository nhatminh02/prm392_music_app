package com.example.prm392_musicapp.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.adapter.LikedMusicAdapter;
import com.example.prm392_musicapp.models.Thumbnails;
import com.example.prm392_musicapp.models.Video;
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

    @SuppressLint("MissingInflatedId")
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

        List<Video> dataList = getAllData();
        if (dataList.size() > 0) {
            // Hiển thị dữ liệu trong RecyclerView
            RecyclerView recyclerView = view.findViewById(R.id.rec_liked_track);
            LikedMusicAdapter adapter = new LikedMusicAdapter(dataList); // Đây là adapter của RecyclerView
            recyclerView.setAdapter(adapter);
            ((ConstraintLayout) view.findViewById(R.id.layout_liked_track)).setVisibility(View.VISIBLE);
            ((ConstraintLayout) view.findViewById(R.id.layout_noLikeTrack)).setVisibility(View.INVISIBLE);
        } else {
            ((ConstraintLayout) view.findViewById(R.id.layout_liked_track)).setVisibility(View.INVISIBLE);
            ((ConstraintLayout) view.findViewById(R.id.layout_noLikeTrack)).setVisibility(View.VISIBLE);
        }



        return view;
    }

    public void setSQLiteOpenHelper(MySQLiteOpenHelper mySQLiteOpenHelper) {
        this.mySQLiteOpenHelper = mySQLiteOpenHelper;
    }

    @SuppressLint("Range")
    public List<Video> getAllData() {
        List<Video> dataList = new ArrayList<>();
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity(), "musicdb", null, 11);
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LikedTracks", null);
        if (cursor.moveToFirst()) {
            do {
                Thumbnails thumbnail;
                String id;
                String videoId;
                String title;
                String channelTitle;

                id = cursor.getString(cursor.getColumnIndex("LTid"));
                videoId = cursor.getString(cursor.getColumnIndex("videoId"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                String thumbnailJson = cursor.getString(cursor.getColumnIndex("thumbnails"));
                channelTitle = cursor.getString(cursor.getColumnIndex("channelTitle"));

                thumbnail = deserializeThumbnail(thumbnailJson);

                Video data = new Video(videoId, title, thumbnail, channelTitle);
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    private Thumbnails deserializeThumbnail(String thumbnailJson) {
        // Sử dụng thư viện Gson để chuyển đổi chuỗi JSON thành đối tượng Thumbnails
        // Đảm bảo rằng thư viện Gson đã được thêm vào dependencies trong file build.gradle

        // Ví dụ:
        Gson gson = new Gson();
        return gson.fromJson(thumbnailJson, Thumbnails.class);

        // Bạn cần triển khai phương thức trên dựa trên cấu trúc JSON của đối tượng Thumbnails trong project của bạn.

        // Trả về null nếu bạn chưa triển khai deserializeThumbnail
    }


}