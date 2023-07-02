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

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.Item;
import com.example.prm392_musicapp.models.Music;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MusicViewHolder> {
    private List<Item> mListMusic;
    Activity activity;

    public SearchAdapter(List<Item> mListMusic, Activity activity) {
        this.mListMusic = mListMusic;
        this.activity = activity;
    }

    public void setSearchList(List<Item> filteredList) {
        this.mListMusic = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Item music = mListMusic.get(position);
        if (music == null) {
            return;
        }
        Glide.with(holder.imgMusic.getContext())
                .load(music.getSnippet().getThumbnails().getMedium().getUrl())
                .into(holder.imgMusic);
        if(music.getSnippet().getTitle().trim().length() > 40){
            holder.tvName.setText(music.getSnippet().getTitle().substring(0,35) + "...");
        }else{
            holder.tvName.setText(music.getSnippet().getTitle());
        }
        holder.tvSinger.setText(music.getSnippet().getChannelTitle());
    }

    @Override
    public int getItemCount() {
        if (mListMusic != null) {
            return mListMusic.size();
        }
        return 0;
    }


    public class MusicViewHolder extends RecyclerView.ViewHolder {
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

