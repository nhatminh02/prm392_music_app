package com.example.prm392_musicapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.Video;

import java.util.List;

public class RecentlyPlayedAdapter extends RecyclerView.Adapter<RecentlyPlayedAdapter.MusicHolder> {

    List<Video> recVideo;
    Activity activity;

    public RecentlyPlayedAdapter(List<Video> recVideo, Activity activity) {
        this.recVideo = recVideo;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecentlyPlayedAdapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_music,
                parent,false);
        return new MusicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        Glide.with(holder.imv_thumb.getContext())
                .load(recVideo.get(position).getThumbnails())
                .into(holder.imv_thumb);
        holder.tv_musname.setText(recVideo.get(position).getTitle());
        holder.tv_singer.setText(recVideo.get(position).getChannelTitle());
    }

    @Override
    public int getItemCount() {
        return recVideo.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {
        ImageView imv_thumb;
        TextView tv_musname;
        TextView tv_singer;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            imv_thumb =itemView.findViewById(R.id.imv_thumb);
            tv_musname =itemView.findViewById(R.id.tv_musname);
            tv_singer =itemView.findViewById(R.id.tv_singer);
        }
    }
}
