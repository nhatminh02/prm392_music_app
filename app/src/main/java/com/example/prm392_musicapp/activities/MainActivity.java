package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.fragments.FragmentHome;
import com.example.prm392_musicapp.fragments.FragmentLibrary;
import com.example.prm392_musicapp.fragments.FragmentSearch;
import com.example.prm392_musicapp.fragments.FragmentSetting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentHome fragmentHome;
    FragmentLibrary fragmentLibrary;
    FragmentSetting fragmentSetting;
    FragmentSearch fragmentSearch;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentHome = FragmentHome.newInstance(null, null);
        fragmentLibrary = FragmentLibrary.newInstance(null, null);
        fragmentSetting = FragmentSetting.newInstance(null, null);
        fragmentSearch = FragmentSearch.newInstance(null, null);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.setting) {
                    transactionFragment(R.id.fr_container, fragmentSetting, "", "REPLACE");
                } else if (item.getItemId() == R.id.library) {
                    transactionFragment(R.id.fr_container, fragmentLibrary, "", "REPLACE");
                } else if (item.getItemId() == R.id.search) {
                    transactionFragment(R.id.fr_container, fragmentSearch, "", "REPLACE");
                } else if (item.getItemId() == R.id.home) {
                    transactionFragment(R.id.fr_container, fragmentHome, "", "REPLACE");
                }
                return true;
            }
        });
        //add homepage fragment khi app chạy
        transactionFragment(R.id.fr_container, fragmentHome, "", "ADD");
    }


    public void transactionFragment(int containerViewId, Fragment fragment, String tag, String action) {
        if (action.equalsIgnoreCase("add")) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(containerViewId, fragment, tag);
            fragmentTransaction.commit();
        } else if (action.equalsIgnoreCase("replace")) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.commit();
        }
    }


}