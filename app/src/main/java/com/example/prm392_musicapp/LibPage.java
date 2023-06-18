package com.example.prm392_musicapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

<<<<<<< Updated upstream:app/src/main/java/com/example/prm392_musicapp/LibPage.java
    public LibPage() {
=======
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    FragmentLoveTrack fragmentLoveTrack;

    public FragmentLibrary() {
>>>>>>> Stashed changes:app/src/main/java/com/example/prm392_musicapp/fragments/FragmentLibrary.java
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LibPage.
     */
    // TODO: Rename and change types and number of parameters
    public static LibPage newInstance(String param1, String param2) {
        LibPage fragment = new LibPage();
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
<<<<<<< Updated upstream:app/src/main/java/com/example/prm392_musicapp/LibPage.java
        View rootView = inflater.inflate(R.layout.fragment_lib_page, container, false);
        List<LibFunc> lbf =new ArrayList<>();
        LibFunc f1 = new LibFunc("Liked Tracks", ">");
        LibFunc f2 = new LibFunc("Playlist", ">");
        LibFunc f3 = new LibFunc("Albums", ">");
        LibFunc f4 = new LibFunc("Following", ">");
        LibFunc f5 = new LibFunc("Station", ">");

        //TODO: add library function to list
        lbf.add(f1);
        lbf.add(f2);
        lbf.add(f3);
        lbf.add(f4);
        lbf.add(f5);

        //View
        FuncAdapter adt = new FuncAdapter(lbf);
        RecyclerView rec = rootView.findViewById(R.id.rec_lib1);
        RecyclerView.LayoutManager layout_manager =  new LinearLayoutManager(rootView.getContext());
        rec.setLayoutManager(layout_manager);
        rec.setAdapter(adt);

        return inflater.inflate(R.layout.fragment_lib_page, container, false);
=======
        View view = inflater.inflate(R.layout.library_page, container, false);

        List<Music> recently = new ArrayList<>();
        Music recent1 = new Music(R.drawable.nnca, "Noi nay co anh", "Son Tung MTP");
        recently.add(recent1);
        MusicAdapter adapterRecently = new MusicAdapter(recently, getActivity());
        RecyclerView rec2 = view.findViewById(R.id.rec_recently);
        RecyclerView.LayoutManager layout_manager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rec2.setLayoutManager(layout_manager2);
        rec2.setAdapter(adapterRecently);

        return view;
>>>>>>> Stashed changes:app/src/main/java/com/example/prm392_musicapp/fragments/FragmentLibrary.java
    }




}