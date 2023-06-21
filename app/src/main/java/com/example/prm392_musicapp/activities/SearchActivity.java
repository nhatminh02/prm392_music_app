package com.example.prm392_musicapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the search query
                // You can perform your search operation here

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the user changes the query text
                // You can perform real-time filtering or suggestions here
                filterList(newText);
                return true;
            }
        });

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
        list.add(new Music(R.drawable.atbe,"Tình em là đại dương","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Kiếp đỏ den","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Phê","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Lời xin lỗi của một dân chơi","Duy Mạnh"));
        list.add(new Music(R.drawable.atbe,"Em của ngày hôm qua","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Chiếc khăn gió ấm","KHánh Phương"));
        list.add(new Music(R.drawable.atbe,"Ta còn yêu nhau","Đức Phúc"));
        list.add(new Music(R.drawable.atbe,"Không phải dạng vừa đâu","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Thái Bình mồ hôi rơi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Lệ anh vẫn rơi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Ấn nút nhớ thả giấc mơ","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Đừng về trễ","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Lạc trôi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Run now","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Nắng âm xa dần","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Tình em là đại dương","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Cơn mưa ngang qua","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Mãi như ngày hôm qua","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Hãy trao cho anh","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Anh sai rồi","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Muộn rồi mà sao còn","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Có chắc yêu là đây","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"KHuôn mặt đáng thương","Sơn Tùng"));
        list.add(new Music(R.drawable.atbe,"Một chút quên em thôi","Sơn Tùng"));

        return list;
    }

    private  void filterList(String text){
        List<Music> filteredList = new ArrayList<>();
        List<Music> songList = getListSong();
        for (Music music : songList){
            if(music.getMusicName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(music);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this,"Not found",Toast.LENGTH_SHORT).show();
        }else {
            searchAdapter.setFilteredList(filteredList);
        }
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
