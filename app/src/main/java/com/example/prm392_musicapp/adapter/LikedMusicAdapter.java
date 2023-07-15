package com.example.prm392_musicapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.activities.VideoPlayActivity;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.fragments.FragmentLikedTracks;
import com.example.prm392_musicapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LikedMusicAdapter extends RecyclerView.Adapter<LikedMusicAdapter.MusicHolder> {
    private final List<Video> dataList;
    String videoId;
    private View.OnClickListener onClickListener;
    MySQLiteOpenHelper mySQLiteOpenHelper;

    public LikedMusicAdapter(List<Video> dataList) {
        this.dataList = dataList;
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Video video);
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
        Glide.with(holder.imv_thumb.getContext())
                .load(dataList.get(position).getThumbnails())
                .into(holder.imv_thumb);
        holder.tv_musname.setText(dataList.get(position).getTitle());
        holder.tv_singer.setText(dataList.get(position).getChannelTitle());
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {
        ImageView imv_thumb;
        TextView tv_musname;
        TextView tv_singer;


        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            imv_thumb = itemView.findViewById(R.id.imv_thumb);
            tv_musname = itemView.findViewById(R.id.tv_musname);
            tv_singer = itemView.findViewById(R.id.tv_singer);
            ((FloatingActionButton)itemView.findViewById(R.id.delete_liked_track)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    mySQLiteOpenHelper = new MySQLiteOpenHelper(itemView.getContext(), "ProjectDB", null, 1);
                    SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
                    db = mySQLiteOpenHelper.getWritableDatabase();
                    db.delete("LikedTracks", "title=?", new String[]{tv_musname.getText().toString()});
                    db.close();

                    dataList.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}
