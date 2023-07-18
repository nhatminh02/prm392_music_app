package com.example.prm392_musicapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.adapter.PlaylistAdapter;
import com.example.prm392_musicapp.adapter.SearchAdapter;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Playlist;
import com.example.prm392_musicapp.models.SearchItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment {
    List<SearchItem> searchList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentChoosePlaylist fragmentChoosePlaylist;
    FragmentHome fragmentHome;
    ProgressBar progressBar;
    TextView startTitleSearch;
    TextView startSubtitleSearch;
    SharedPreferences sharedPreferences;
    MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    boolean darkMode;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchAdapter searchAdapter;
    private SearchView searchView;

    public FragmentSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();
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
        openHelper = new MySQLiteOpenHelper(getActivity(), "ProjectDB", null, 1);
        View view = inflater.inflate(R.layout.search_page, container, false);
        final Handler handler = new Handler();
        progressBar = view.findViewById(R.id.search_progress_bar);
        searchAdapter = new SearchAdapter(searchList, getActivity());
        RecyclerView revMusic = view.findViewById(R.id.rev_music);
        searchView = view.findViewById(R.id.searchView);
        startTitleSearch = view.findViewById(R.id.start_title_search);
        startSubtitleSearch = view.findViewById(R.id.start_subtitle_search);

        SharedPreferences sharedPreferencesId = getActivity().getSharedPreferences("IdSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesIdEdit = sharedPreferencesId.edit();

        handleSwitchTheme();
        //Lấy ra id của video khi click vào
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String itemId) {
                Log.i("itemId", itemId);
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("itemId", itemId);
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
                    sharedPreferencesIdEdit.putString("currentId", itemId);
                    sharedPreferencesIdEdit.putString("prevId", itemId);
                } else if (currentID != null && prevID != null) {
                    if (!currentID.equals(itemId) && !isSuffle) {
                        sharedPreferencesIdEdit.putString("prevId", currentID);
                        sharedPreferencesIdEdit.putString("currentId", itemId);
                    } else if (currentID.equals(itemId) && !isSuffle) {
                        sharedPreferencesIdEdit.putString("prevId", itemId);
                    } else if (isSuffle) {
                        if (!itemId.equals(currentID)) {
                            sharedPreferencesIdEdit.putString("prevId", currentID);
                            sharedPreferencesIdEdit.putString("currentId", itemId);
                            sharedPrefSuffleEdit.putBoolean("isSuffle", false);
                        } else {
                            sharedPrefSuffleEdit.putBoolean("isSuffle", false);
                        }
                        sharedPrefSuffleEdit.apply();
                    } else if (isSkip) {
                        if (!itemId.equals(currentID)) {
                            sharedPreferencesIdEdit.putString("prevId", currentID);
                            sharedPreferencesIdEdit.putString("currentId", itemId);
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

        searchAdapter.setOnOptionClickListener(new SearchAdapter.OnOptionClickListener() {
            @Override
            public void onOptionClick(int position, SearchItem music) {
                // Xử lý sự kiện khi người dùng nhấn vào một tùy chọn
                // Sử dụng position để định danh cho item được chọn trong mListMusic
                if (position == 1) {
                    //SearchItem music;
                    String itemId = music.getId().getVideoId(); // Retrieve the ID of the selected item
                    db = openHelper.getWritableDatabase();
                    db.delete("LikedTracks", "videoId=?", new String[]{itemId});
                    String sql = "insert into LikedTracks(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                    db.execSQL(sql, new String[]{itemId, music.getSnippet().getTitle(), music.getSnippet().getThumbnails().getMedium().getUrl(), music.getSnippet().getChannelTitle()});
                    db.close();
                    Toast.makeText(getContext(), "Added to like tracks successfully", Toast.LENGTH_SHORT).show();

                }
                if (position == 0) {
                    openHelper = new MySQLiteOpenHelper(getActivity(), "ProjectDB", null, 1);
                    SQLiteDatabase db = openHelper.getReadableDatabase();
                    String itemId = music.getId().getVideoId();
                    db = openHelper.getWritableDatabase();
                    String query = "SELECT * FROM PlaylistMusic WHERE PLMvideoId = ?";
                    String[] selectionArgs1 = {itemId};
                    Cursor cursor = db.rawQuery(query, selectionArgs1);
                    boolean exists = cursor.moveToFirst();
                    if(!exists){
                        String sql = "insert into PlaylistMusic(PLMvideoId,PLMtitle,PLMthumbnails,PLMchannelTitle) values(?,?,?,?)";
                        db.execSQL(sql, new String[]{itemId, music.getSnippet().getTitle(), music.getSnippet().getThumbnails().getMedium().getUrl(), music.getSnippet().getChannelTitle()});
                        db.close();
                    }
                    Log.d("asdcfvgbnm", itemId);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentChoosePlaylist = FragmentChoosePlaylist.newInstance(null, null);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fr_container, fragmentChoosePlaylist, "");
                    fragmentTransaction.commit();


                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //khi người dùng nhấn enter trên bàn phím sẽ xử lý trong này
                //set loading khi call API
                displaySearchTitle(false, getResources().getString(R.string.start_title_search),
                        getResources().getString(R.string.start_subtitle_search));
                progressBar.setVisibility(View.VISIBLE);
                revMusic.setVisibility(View.INVISIBLE);
                VideoDataUtils.searchVideoData(query).observe(getViewLifecycleOwner(), new Observer<List<SearchItem>>() {
                    @Override
                    public void onChanged(List<SearchItem> searchItems) {
                        if (searchItems.size() == 0) {
                            displaySearchTitle(true, getResources().getString(R.string.not_found_title),
                                    getResources().getString(R.string.not_found_subtitle));
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        //tắt loading khi đã nhận data
                        progressBar.setVisibility(View.GONE);
                        revMusic.setVisibility(View.VISIBLE);

                        searchList = searchItems;
                        searchAdapter.setSearchList(searchList);

                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //khi người dùng đang nhập sẽ xử lý trong này
                if (newText.length() == 0) {
                    return true;
                }
                // delay goi API lại 1s khi người dùng thay đổi giá trị search (tránh gọi API liên tục)
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        VideoDataUtils.searchVideoData(newText).observe(getViewLifecycleOwner(), new Observer<List<SearchItem>>() {
                            @Override
                            public void onChanged(List<SearchItem> searchItems) {
                                if (searchItems.size() == 0) {
                                    displaySearchTitle(true, getResources().getString(R.string.not_found_title),
                                            getResources().getString(R.string.not_found_subtitle));
                                    progressBar.setVisibility(View.GONE);
                                    revMusic.setVisibility(View.INVISIBLE);
                                    return;
                                }
                                displaySearchTitle(false, getResources().getString(R.string.start_title_search),
                                        getResources().getString(R.string.start_subtitle_search));
                                searchList = searchItems;
                                searchAdapter.setSearchList(searchList);
                                revMusic.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                }, 1000);
                return true;
            }
        });
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        revMusic.setLayoutManager(linearLayoutManager);
        revMusic.setAdapter(searchAdapter);


        return view;
    }

    public void handleSwitchTheme() {
        //chỉnh theme thanh search
        sharedPreferences = getActivity().getSharedPreferences("mode", Context.MODE_PRIVATE);
        darkMode = sharedPreferences.getBoolean("dark", false);
        if (darkMode) {
            searchView.setBackgroundResource(R.drawable.btn_search_round_dark);
        } else {
            searchView.setBackgroundResource(R.drawable.btn_search_round_light);
        }
    }

    public void displaySearchTitle(boolean isDisplay, String title, String subTitle) {
        startTitleSearch.setText(title);
        startSubtitleSearch.setText(subTitle);
        if (isDisplay) {
            startTitleSearch.setVisibility(View.VISIBLE);
            startSubtitleSearch.setVisibility(View.VISIBLE);
        } else {
            startTitleSearch.setVisibility(View.GONE);
            startSubtitleSearch.setVisibility(View.GONE);
        }
    }
}