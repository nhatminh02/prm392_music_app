package com.example.prm392_musicapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.activities.HomeActivity;
import com.example.prm392_musicapp.adapter.MusicAdapter;
import com.example.prm392_musicapp.models.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

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

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setContentView(R.layout.home_page);

        List<Music> reccomends = new ArrayList<>();
        Music rm1 = new Music(R.drawable.atbe, "Am tham ben em", "Son Tung MTP");
        Music rm2 = new Music(R.drawable.cadsv, "Chac ai do se ve", "Son Tung MTP");
        Music rm3 = new Music(R.drawable.ctktvn, "Chung ta khong thuoc ve nhau", "Son Tung MTP");
        reccomends.add(rm1);
        reccomends.add(rm3);
        reccomends.add(rm2);
        MusicAdapter adapterReccomnend = new MusicAdapter(reccomends, activity);
        RecyclerView rec1 = activity.findViewById(R.id.rec_reccomend);
        RecyclerView.LayoutManager layout_manager1 = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        rec1.setLayoutManager(layout_manager1);
        rec1.setAdapter(adapterReccomnend);

        List<Music> recently = new ArrayList<>();
        Music recent1 = new Music(R.drawable.nnca, "Noi nay co anh", "Son Tung MTP");
        recently.add(recent1);
        MusicAdapter adapterRecently = new MusicAdapter(recently, activity);
        RecyclerView rec2 = activity.findViewById(R.id.rec_recently);
        RecyclerView.LayoutManager layout_manager2 = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        rec2.setLayoutManager(layout_manager2);
        rec2.setAdapter(adapterRecently);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_page, container, false);


    }
}