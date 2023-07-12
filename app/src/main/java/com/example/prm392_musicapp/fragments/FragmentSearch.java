package com.example.prm392_musicapp.fragments;

<<<<<<< Updated upstream
import android.app.SearchManager;
import android.content.Context;
=======
import android.annotation.SuppressLint;
import android.content.Intent;
>>>>>>> Stashed changes
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
<<<<<<< Updated upstream
import android.widget.Toast;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.adapter.SearchAdapter;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Item;
import com.example.prm392_musicapp.models.Music;
=======
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.activities.MainActivity;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.adapter.IClickBottomSheetListener;
import com.example.prm392_musicapp.adapter.SearchAdapter;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Item_model_bottom_sheet;
import com.example.prm392_musicapp.models.SearchItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
<<<<<<< Updated upstream
public class FragmentSearch extends Fragment {
    List<Item> searchList;
=======
public class FragmentSearch extends Fragment  {
    List<SearchItem> searchList;
>>>>>>> Stashed changes
    ProgressBar progressBar;

    private BottomSheetBehavior bottomSheetBehavior;


    AppCompatActivity appCompatActivity = new AppCompatActivity();

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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_page, container, false);
        final Handler handler = new Handler();
        progressBar = view.findViewById(R.id.search_progress_bar);
        searchAdapter = new SearchAdapter(searchList, getActivity(), appCompatActivity.getSupportFragmentManager());
        RecyclerView revMusic = view.findViewById(R.id.rev_music);
        searchView = view.findViewById(R.id.searchView);
<<<<<<< Updated upstream
=======
        startTitleSearch = view.findViewById(R.id.start_title_search);
        startSubtitleSearch = view.findViewById(R.id.start_subtitle_search);
//        Button btnOpenModelSheet = view.findViewById(R.id.bottomSheetButton);
//        btnOpenModelSheet.setOnClickListener(this);
////
//        btnOpenModelSheet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickOpenBottomSheetFragment();
//            }
//        });
        //Lấy ra id của video khi click vào
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String itemId) {
                Log.i("itemId", itemId);
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
            }

        });
>>>>>>> Stashed changes


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //khi người dùng nhấn enter trên bàn phím sẽ xử lý trong này
                //set loading khi call API
                progressBar.setVisibility(View.VISIBLE);
                VideoDataUtils.searchVideoData(query).observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
                    @Override
                    public void onChanged(List<Item> items) {
                        //tắt loading khi đã nhận data
                        progressBar.setVisibility(View.GONE);
                        searchList = items;
                        searchAdapter.setSearchList(searchList);
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //khi người dùng đang nhập sẽ xử lý trong này
                // delay goi API lại 1s khi người dùng thay đổi giá trị search (tránh gọi API liên tục)
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       VideoDataUtils.searchVideoData(newText).observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
                           @Override
                           public void onChanged(List<Item> items) {
                               //data o trong cai items
                               searchList = items;
                               searchAdapter.setSearchList(searchList);
                           }
                       });
                    }
                },1000);
                return true;
            }
        });
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        revMusic.setLayoutManager(linearLayoutManager);
        revMusic.setAdapter(searchAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        revMusic.addItemDecoration(itemDecoration);

        return view;
    }

<<<<<<< Updated upstream




=======
    public void displaySearchTitle(boolean isDisplay, String title, String subTitle){
        startTitleSearch.setText(title);
        startSubtitleSearch.setText(subTitle);
        if(isDisplay){
            startTitleSearch.setVisibility(View.VISIBLE);
            startSubtitleSearch.setVisibility(View.VISIBLE);
        }else{
            startTitleSearch.setVisibility(View.GONE);
            startSubtitleSearch.setVisibility(View.GONE);
        }
    }


//    @Override
//    public void onClick(View v) {
//        int viewId = v.getId();
//        if (viewId == R.id.bottomSheetButton) {
//            clickOpenBottomSheetFragment();
//        }
//    }
>>>>>>> Stashed changes
}