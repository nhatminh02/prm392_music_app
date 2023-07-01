package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.fragments.FragmentHome;
import com.example.prm392_musicapp.fragments.FragmentLibrary;
import com.example.prm392_musicapp.fragments.FragmentLikedTracks;
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
    FragmentLikedTracks fragmentLikedTracks;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout miniBarPlayer, homePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentHome = FragmentHome.newInstance(null, null);
        fragmentLibrary = FragmentLibrary.newInstance(null, null);
        fragmentSetting = FragmentSetting.newInstance(null, null);
        fragmentSearch = FragmentSearch.newInstance(null, null);
        fragmentLikedTracks = FragmentLikedTracks.newInstance(null, null);

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
        miniBarPlayer = findViewById(R.id.mini_player_bar);
        homePage = findViewById(R.id.homePage);
    }


    public void onClickMini(View v){
        Intent intent = new Intent(MainActivity.this, VideoPlayActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(MainActivity.this, miniBarPlayer,ViewCompat.getTransitionName(miniBarPlayer));
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        // Apply the animation to the parent layout
        homePage.startAnimation(slideUp);
        bottomNavigationView.startAnimation(slideDown);
        startActivity(intent, optionsCompat.toBundle());
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("state", "onStart");

    }

    public void onMyButtonClick() {
        ((Button) findViewById(R.id.btn_liked_tracks)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fr_container, fragmentLikedTracks,"");
                fragmentTransaction.commit();
            }
        });
    }

}