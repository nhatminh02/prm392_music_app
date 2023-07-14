package com.example.prm392_musicapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.models.Video;

import java.util.List;

public class LikedMusicAdapter extends RecyclerView.Adapter<LikedMusicAdapter.MusicHolder> {

    private Context context;
    private final List<Video> dataList;
    String videoId;

    Activity activity;

    public LikedMusicAdapter(List<Video> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public LikedMusicAdapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_liked_music,
                parent, false);
        return new MusicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        videoId = dataList.get(position).getVideoId();
        Glide.with(holder.imv_thumb.getContext())
                .load(dataList.get(position).getThumbnails())
                .into(holder.imv_thumb);
        holder.tv_musname.setText(dataList.get(position).getTitle());
        holder.tv_singer.setText(dataList.get(position).getChannelTitle());

        holder.cons_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("itemId", videoId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cons_music;
        ImageView imv_thumb;
        TextView tv_musname;
        TextView tv_singer;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            imv_thumb = itemView.findViewById(R.id.imv_thumb);
            tv_musname = itemView.findViewById(R.id.tv_musname);
            tv_singer = itemView.findViewById(R.id.tv_singer);
            cons_music = itemView.findViewById(R.id.cons_music);
        }
    }
}
