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
import com.example.prm392_musicapp.models.Video;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MusicHolder> {

    List<Video> recommendVideo;
    Activity activity;
    private RecommendAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(RecommendAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecommendAdapter(List<Video> recommendVideo, Activity activity) {
        this.recommendVideo = recommendVideo;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecommendAdapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_music,
                parent,false);
        return new MusicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.MusicHolder holder, int position) {
        Glide.with(holder.imv_thumb.getContext())
                .load(recommendVideo.get(position).getThumbnails())
                .into(holder.imv_thumb);
        holder.tv_musname.setText(recommendVideo.get(position).getTitle());

        holder.tv_singer.setText(recommendVideo.get(position).getChannelTitle());
    }

    @Override
    public int getItemCount() {
        return recommendVideo.size();
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
            Log.i("RecommendAdapter", "Item clicked");
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Video video = recommendVideo.get(position);
                String videoId = video.getVideoId(); // Retrieve the ID of the clicked item
                listener.onItemClick(videoId); // Callback the listener with the item ID
            }
        }
    }
}
