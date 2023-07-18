package com.example.prm392_musicapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.adapter.LikedMusicAdapter;
import com.example.prm392_musicapp.adapter.PlaylistAdapter;
import com.example.prm392_musicapp.models.Playlist;
import com.example.prm392_musicapp.models.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPlaylist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPlaylist extends Fragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentLikedTracks fragmentLikedTracks;
    FragmentPlaylistDetail fragmentPlaylistDetail;
    MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    FragmentLibrary fragmentLibrary;
    ImageView btnBack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPlaylist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPlaylist.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPlaylist newInstance(String param1, String param2) {
        FragmentPlaylist fragment = new FragmentPlaylist();
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
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentLibrary = new FragmentLibrary();
        SharedPreferences sharedPreferencesPlaylistId = getActivity().getSharedPreferences("PlaylistID", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesPlaylistIdEdit = sharedPreferencesPlaylistId.edit();
        ((ImageView) view.findViewById(R.id.btn_comeback)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fr_container, fragmentLibrary, "");
                fragmentTransaction.commit();
            }
        });


        List<Playlist> dataList = new ArrayList<>();
        openHelper = new MySQLiteOpenHelper(getActivity(), "ProjectDB", null, 1);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PLaylists", null);
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(String.valueOf(cursor.getInt(0)));
            String name = cursor.getString(1);
            Playlist data = new Playlist(id, name);
            dataList.add(data);
        }
        cursor.close();
        if (dataList.size() > 0) {
            // Hiển thị dữ liệu trong RecyclerView
            RecyclerView recyclerView = view.findViewById(R.id.rec_playlist);
            PlaylistAdapter adapter = new PlaylistAdapter(dataList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new PlaylistAdapter.OnClickListener() {
                @Override
                public void onClick(int PLid) {
                    Log.d("dfgh", String.valueOf(PLid));
                    sharedPreferencesPlaylistIdEdit.putInt("PLid" ,PLid);
                    sharedPreferencesPlaylistIdEdit.apply();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentPlaylistDetail = FragmentPlaylistDetail.newInstance(null, null);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fr_container, fragmentPlaylistDetail, "");
                    fragmentTransaction.commit();
                }
            });

            ((ConstraintLayout) view.findViewById(R.id.layout_playlist)).setVisibility(View.VISIBLE);
            ((ConstraintLayout) view.findViewById(R.id.layout_noPlaylist)).setVisibility(View.INVISIBLE);
        } else {
            ((ConstraintLayout) view.findViewById(R.id.layout_playlist)).setVisibility(View.INVISIBLE);
            ((ConstraintLayout) view.findViewById(R.id.layout_noPlaylist)).setVisibility(View.VISIBLE);
        }

        ((Button) view.findViewById(R.id.btn_ctrPlaylist)).setOnClickListener(this::onClicked);
        ((Button) view.findViewById(R.id.btn_add)).setOnClickListener(this::onClicked);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mode", Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean("dark", false);
        btnBack = view.findViewById(R.id.btn_comeback);
        if (darkMode) {
            btnBack.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_light);
        } else {
            btnBack.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_dark);
        }

        return view;
    }

    public void onClicked(View view) {
        PopupWindow popupWindow;
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_create_playlist, null);
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        // Hiển thị cửa sổ nhỏ tại vị trí mong muốn
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupView.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {     
            @Override
            public void onClick(View v) {
                String playlistName = ((EditText) popupView.findViewById(R.id.edt_namePlaylist)).getText().toString();
                openHelper = new MySQLiteOpenHelper(getActivity(), "ProjectDB", null, 1);
                db = openHelper.getWritableDatabase();
                String sql = "SELECT * FROM Playlists WHERE PLName = ?";
                Cursor c = db.rawQuery(sql, new String[]{"%" + playlistName + "%"});
                List<Playlist> list = new ArrayList<>();
                while (c.moveToNext()) {
                    int id = c.getInt(0);
                    String name = c.getString(1);
                    list.add(new Playlist(id, name));
                }
                if (list.size() != 0 || TextUtils.isEmpty(((EditText) popupView.findViewById(R.id.edt_namePlaylist)).getText())) {
                    ((TextView) popupView.findViewById(R.id.edt_namePlaylist)).setError("Đã tồn tại hoặc chưa điền");
                    ((TextView) popupView.findViewById(R.id.edt_namePlaylist)).requestFocus();
                } else {
                    String sqlAdd = "insert into PLaylists(PLName) values(?)";
                    db.execSQL(sqlAdd, new String[]{playlistName});
                    db.close();
                    popupWindow.dismiss();
                }
            }
        });


        popupView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}