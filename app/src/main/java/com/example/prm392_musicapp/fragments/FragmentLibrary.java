package com.example.prm392_musicapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.adapter.RecentlyPlayedAdapter;
import com.example.prm392_musicapp.models.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLibrary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLibrary extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase db;
    FragmentLikedTracks fragmentLikedTracks;
    FragmentPlaylist fragmentPlaylist;

//    private MyButtonClickListener myButtonClickListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLibrary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLibrary.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLibrary newInstance(String param1, String param2) {
        FragmentLibrary fragment = new FragmentLibrary();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.library_page, container, false);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext(), "ProjectDB", null, 1);
        db = mySQLiteOpenHelper.getReadableDatabase();
        //lay ra list recently
        String sql = "select * from Recently order by recId desc";
        List<Video> recently = new ArrayList<>();
        Cursor c = db.rawQuery(sql,null);
        while(c.moveToNext()){
            String videoId = c.getString(1);
            String title = c.getString(2);
            String thumbnails = c.getString(3);
            String channelTitle = c.getString(4);
            recently.add(new Video(videoId,title,thumbnails,channelTitle));
        }
        RecentlyPlayedAdapter adapterRecently = new RecentlyPlayedAdapter(recently, getActivity());
        //click de phat
        adapterRecently.setOnItemClickListener(new RecentlyPlayedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String videoId) {
                Log.i("run1", "onItemClick" + videoId);
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("itemId", videoId);
                startActivity(intent);
            }
        });
        RecyclerView rec2 = view.findViewById(R.id.rec_recently);
        RecyclerView.LayoutManager layout_manager2 =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rec2.setLayoutManager(layout_manager2);
        rec2.setAdapter(adapterRecently);

        Button button = view.findViewById(R.id.btn_liked_tracks);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define behavior for when the button is clicked
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentLikedTracks = new FragmentLikedTracks();
                fragmentLikedTracks.setSQLiteOpenHelper(mySQLiteOpenHelper);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fr_container, fragmentLikedTracks, "");
                fragmentTransaction.commit();
            }
        });

        ((Button)view.findViewById(R.id.btn_playlist)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define behavior for when the button is clicked
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentPlaylist = new FragmentPlaylist();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fr_container, fragmentPlaylist, "");
                fragmentTransaction.commit();
            }
        });
      
        return view;
    }



}