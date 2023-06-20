package com.example.prm392_musicapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.prm392_musicapp.R;

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

    FragmentLibrary fragmentLibrary;
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
        ((ImageView)view.findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fr_container, fragmentLibrary, "");
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}