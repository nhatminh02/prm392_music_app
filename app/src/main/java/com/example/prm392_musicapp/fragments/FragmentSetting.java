package com.example.prm392_musicapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.activities.Contact;
import com.example.prm392_musicapp.activities.SettingNotificationActivity;
import com.example.prm392_musicapp.activities.SettingThemeActivity;
import com.example.prm392_musicapp.activities.Term;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment {

    Button themeSettingBtn;
    Button legal;
    Button btn_notification;
    Button contactsp;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSetting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSetting.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSetting newInstance(String param1, String param2) {
        FragmentSetting fragment = new FragmentSetting();
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

        View view = inflater.inflate(R.layout.setting_page, container, false);
        btn_notification=view.findViewById(R.id.btn_notification);
        contactsp=view.findViewById(R.id.contactsp);
        legal=view.findViewById(R.id.legal);
        themeSettingBtn = view.findViewById(R.id.btn_theme);
        themeSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingThemeActivity.class);
                startActivity(intent);
            }

        });
        legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Term.class);
                startActivity(intent);

            }
        });
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingNotificationActivity.class);
                startActivity(intent);

            }
        });
        contactsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Contact.class);
                startActivity(intent);
            }
        });
        return view;
    }
}