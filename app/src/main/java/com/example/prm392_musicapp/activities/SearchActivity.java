package com.example.prm392_musicapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.adapter.SearchAdapter;
import com.example.prm392_musicapp.models.Music;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView revMusic;
    private SearchAdapter searchAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        revMusic = findViewById(R.id.rev_music);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
         revMusic.setLayoutManager(linearLayoutManager);

         searchAdapter = new SearchAdapter(getListSong(),this);
         revMusic.setAdapter(searchAdapter);

         RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
         revMusic.addItemDecoration(itemDecoration);


    }

    private List<Music> getListSong() {
        List<Music> list = new ArrayList<>();
        list.add(new Music(R.drawable.nnca,"Nơi này có anh","Sơn Tùng"));
        list.add(new Music(R.drawable.cadsv,"Chắc ai đó sẽ về","Sơn Tùng"));
        list.add(new Music(R.drawable.ctktvn,"Chúng ta không thuộc về nhau","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Âm thầm bên em","Sơn Tùng"));

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_song_menu,menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
