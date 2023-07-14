package com.example.prm392_musicapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.activities.CreatePlaylist;
import com.example.prm392_musicapp.activities.MainActivity;
import com.example.prm392_musicapp.activities.VideoPlayActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPlaylist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPlaylist extends Fragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentLikedTracks fragmentLikedTracks;

    FragmentLibrary fragmentLibrary;

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
        ((ImageView) view.findViewById(R.id.btn_comeback)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fr_container, fragmentLibrary, "");
                fragmentTransaction.commit();
            }
        });

        ((Button) view.findViewById(R.id.btn_ctrPlaylist)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    
            }
        });

        return view;
    }
}