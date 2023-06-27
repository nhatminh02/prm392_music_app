package com.example.prm392_musicapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.Music;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.MusicViewHolder> {
    private List<Music> mListMusic;
    Activity activity;

    public SearchAdapter(List<Music> mListMusic, Activity activity) {
        this.mListMusic = mListMusic;
        this.activity = activity;
    }
   public  void  setFilteredList(List<Music> filteredList){
        this.mListMusic = filteredList;
       notifyDataSetChanged();
   }
    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music music = mListMusic.get(position);
        if (music == null) {
            return;
        }
        holder.imgMusic.setImageResource(music.getThumbnail());
        holder.tvName.setText(music.getMusicName());
        holder.tvSinger.setText(music.getSinger());

    }

    @Override
    public int getItemCount() {
        if(mListMusic != null){
            return mListMusic.size();
        }
        return 0;
    }



    public class MusicViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgMusic;
        TextView tvName;
        TextView tvSinger;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMusic = itemView.findViewById(R.id.tv_search_thumb);
            tvName = itemView.findViewById(R.id.tv_search_music);
            tvSinger = itemView.findViewById(R.id.tv_search_singer);
        }
    }



    }

