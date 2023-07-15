package com.example.prm392_musicapp.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.SearchItem;
import com.example.prm392_musicapp.models.Video;

import java.util.List;

public class RecentlyPlayedAdapter extends RecyclerView.Adapter<RecentlyPlayedAdapter.MusicHolder> {

    List<Video> recVideo;
    Activity activity;
    private RecentlyPlayedAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(RecentlyPlayedAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

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

    public class MusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imv_thumb;
        TextView tv_musname;
        TextView tv_singer;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imv_thumb =itemView.findViewById(R.id.imv_thumb);
            tv_musname =itemView.findViewById(R.id.tv_musname);
            tv_singer =itemView.findViewById(R.id.tv_singer);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            Log.i("RecentlyPlayedAdapter", "Item clicked");
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Video video = recVideo.get(position);
                String videoId = video.getVideoId(); // Retrieve the ID of the clicked item
                listener.onItemClick(videoId); // Callback the listener with the item ID
            }
        }
    }
}
