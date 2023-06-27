package com.example.prm392_musicapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.adapter.SearchAdapter;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Item;
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

    private  SearchAdapter searchAdapter;
    private  SearchView  searchView;

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

//          VideoDataUtils.searchVideoData("bac").observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
//      @Override
//      public void onChanged(List<Item> items) {
//              //data ở trong cái items
//          Log.i("data",items.toString());
//      }
//  });

       searchAdapter = new SearchAdapter(getListSong(),getActivity());
        RecyclerView revMusic = view.findViewById(R.id.rev_music);
       searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the search query
                // You can perform your search operation here

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the user changes the query text
                // You can perform real-time filtering or suggestions here
                filterList(newText);
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

    private List<Music> getListSong() {
        List<Music> list = new ArrayList<>();
        list.add(new Music(R.drawable.nnca,"Nơi này có anh","Sơn Tùng"));
        list.add(new Music(R.drawable.cadsv,"Chắc ai đó sẽ về","Sơn Tùng"));
        list.add(new Music(R.drawable.ctktvn,"Chúng ta không thuộc về nhau","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Âm thầm bên em","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Tình em là đại dương","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Kiếp đỏ den","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Phê","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Lời xin lỗi của một dân chơi","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Em của ngày hôm qua","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Chiếc khăn gió ấm","KHánh Phương"));
        list.add(new Music(R.drawable.atbe,"Ta còn yêu nhau","Đức Phúc"));
        list.add(new Music(R.drawable.atbe,"Không phải dạng vừa đâu","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Thái Bình mồ hôi rơi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Lệ anh vẫn rơi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Ấn nút nhớ thả giấc mơ","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Đừng về trễ","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Lạc trôi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Run now","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Nắng âm xa dần","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Tình em là đại dương","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Cơn mưa ngang qua","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Mãi như ngày hôm qua","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Hãy trao cho anh","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Anh sai rồi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Muộn rồi mà sao còn","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Có chắc yêu là đây","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"KHuôn mặt đáng thương","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Một chút quên em thôi","Sơn Tùng"));

        return list;
    }

    private  void filterList(String text){
        List<Music> filteredList = new ArrayList<>();
        List<Music> songList = getListSong();
        for (Music music : songList){
            if(music.getMusicName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(music);
            }
        }
        if(!filteredList.isEmpty()){
            searchAdapter.setFilteredList(filteredList);
        }
    }

}