package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.prm392_musicapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    HomeActivity homeActivity;
    LibraryActivity libraryActivity;
    SettingActivity settingActivity;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        homeActivity = HomeActivity.newInstance(null, null);
        libraryActivity = LibraryActivity.newInstance(null, null);
        settingActivity = SettingActivity.newInstance(null,null);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.setting) {
                    Log.i("frag", "setting");
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fr_container, settingActivity, "");
                    fragmentTransaction.commit();
                } else if (item.getItemId() == R.id.library) {
                    Log.i("frag", "lib");
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fr_container, libraryActivity, "");
                    fragmentTransaction.commit();
                } else if (item.getItemId() == R.id.search) {
                    Log.i("frag", "search");
                } else if (item.getItemId() == R.id.home) {
                    Log.i("frag", "home");
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fr_container, homeActivity, "");
                    fragmentTransaction.commit();
                }
                return true;
            }
        });

        //add homepage fragment khi app chạy
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fr_container, homeActivity, "");
        fragmentTransaction.commit();
    }


}