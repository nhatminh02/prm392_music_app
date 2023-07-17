package com.example.prm392_musicapp.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.adapter.PlaylistAdapter;
import com.example.prm392_musicapp.models.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChoosePlaylist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChoosePlaylist extends Fragment {
    MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentChoosePlaylist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentChoosePlaylist.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentChoosePlaylist newInstance(String param1, String param2) {
        FragmentChoosePlaylist fragment = new FragmentChoosePlaylist();
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
        View view = inflater.inflate(R.layout.choose_playlist, container, false);
        openHelper = new MySQLiteOpenHelper(getActivity(), "ProjectDB", null, 1);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        List<Playlist> dataList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM PLaylists", null);
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(String.valueOf(cursor.getInt(0)));
            String name = cursor.getString(1);
            Playlist data = new Playlist(id, name);
            dataList.add(data);
        }
        cursor.close();
        RecyclerView recyclerView = view.findViewById(R.id.rec_choose_playlist);
        PlaylistAdapter adapter = new PlaylistAdapter(dataList);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}