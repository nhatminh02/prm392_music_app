package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.adapter.MusicAdapter;
import com.example.prm392_musicapp.models.Music;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        List<Music> reccomends = new ArrayList<>();
        Music rm1 = new Music(R.drawable.atbe, "Am tham ben em", "Son Tung MTP");
        Music rm2 = new Music(R.drawable.cadsv, "Chac ai do se ve", "Son Tung MTP");
        Music rm3 = new Music(R.drawable.ctktvn, "Chung ta khong thuoc ve nhau", "Son Tung MTP");
        reccomends.add(rm1);
        reccomends.add(rm3);
        reccomends.add(rm2);
        MusicAdapter adapterReccomnend = new MusicAdapter(reccomends, this);
        RecyclerView rec1 = findViewById(R.id.rec_reccomend);
        RecyclerView.LayoutManager layout_manager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec1.setLayoutManager(layout_manager1);
        rec1.setAdapter(adapterReccomnend);

        List<Music> recently = new ArrayList<>();
        Music recent1 = new Music(R.drawable.nnca, "Noi nay co anh", "Son Tung MTP");
        recently.add(recent1);
        MusicAdapter adapterRecently = new MusicAdapter(recently, this);
        RecyclerView rec2 = findViewById(R.id.rec_recently);
        RecyclerView.LayoutManager layout_manager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec2.setLayoutManager(layout_manager2);
        rec2.setAdapter(adapterRecently);
        bottomNavigationView = findViewById(R.id.bottom_nav);
       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId() == R.id.setting){
                   Log.i("frag", "setting");
               } else if (item.getItemId() == R.id.library) {
                   Log.i("frag", "lib");
               } else if (item.getItemId() == R.id.search) {
                   Log.i("frag", "search");
               } else if (item.getItemId() == R.id.home) {
                   Log.i("frag", "home");
               }
               return true;
           }
       });
    }
}