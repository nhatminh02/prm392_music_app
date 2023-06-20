package com.example.prm392_musicapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.adapter.SearchAdapter;
import com.example.prm392_musicapp.models.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment {

    AppCompatActivity appCompatActivity = new AppCompatActivity();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View view = inflater.inflate(R.layout.search_page, container, false);

        SearchAdapter searchAdapter = new SearchAdapter(getListSong(),getActivity());
        RecyclerView revMusic = view.findViewById(R.id.rev_music);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        revMusic.setLayoutManager(linearLayoutManager);
        revMusic.setAdapter(searchAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        revMusic.addItemDecoration(itemDecoration);

        return view;
    }

    private List<Music> getListSong() {
        List<Music> list = new ArrayList<>();
        list.add(new Music(R.drawable.nnca,  "Nơi này có anh", "Sơn Tùng"));
        list.add(new Music(R.drawable.cadsv, "Chắc ai đó sẽ về", "Sơn Tùng"));
        list.add(new Music(R.drawable.ctktvn, "Chúng ta không thuộc về nhau", "Sơn Tùng"));
        list.add(new Music(R.drawable.atbe, "Âm thầm bên em", "Sơn Tùng"));
        return list;
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        SearchAdapter searchAdapter = new SearchAdapter(getListSong(),getActivity());
        appCompatActivity.getMenuInflater().inflate(R.menu.search_song_menu, menu);

        SearchManager searchManager = (SearchManager) appCompatActivity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(appCompatActivity.getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

//    SearchView searchView;

//    public void onBackPressed() {
//        ComponentActivity componentActivity = new ComponentActivity();
//        if (!searchView.isIconified()) {
//            searchView.setIconified(true);
//            return;
//        }
//        super.onBackPressed();
//    }

}