package com.example.prm392_musicapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.models.Playlist;
import com.example.prm392_musicapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlaylistMusicAdapter extends RecyclerView.Adapter<PlaylistMusicAdapter.MusicHolder> {

    private final List<Video> dataList;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    private PlaylistMusicAdapter.OnItemClickListener listener;
    Activity activity;
    private SharedPreferences sharedPreferences;

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(PlaylistMusicAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public PlaylistMusicAdapter(List<Video> dataList, SharedPreferences sharedPreferences) {
        this.dataList = dataList;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public PlaylistMusicAdapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_playlist_music,
                parent,false);
        return new MusicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, @SuppressLint("RecyclerView") int position) {
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
            int plid = sharedPreferences.getInt("PLid", 0);

            ((ImageView)itemView.findViewById(R.id.delete_liked_track)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    int id = 0;
                    String videoId = dataList.get(position).getVideoId();
                    mySQLiteOpenHelper = new MySQLiteOpenHelper(itemView.getContext(), "ProjectDB", null, 1);
                    SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("select PLMid from PlaylistMusic where PLMvideoId=?", new String[]{videoId});
                    while (cursor.moveToNext()) {
                        id = Integer.parseInt(String.valueOf(cursor.getInt(0)));
                    }
                    db = mySQLiteOpenHelper.getWritableDatabase();
                    db.delete("PlaylistMus", "PLMid=? and PLid = ?", new String[]{String.valueOf(id), String.valueOf(plid)});
                    db.close();
                    Toast.makeText(itemView.getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();

                    dataList.remove(position);
                    notifyItemRemoved(position);
                }
            });

        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            Log.i("RecentlyPlayedAdapter", "Item clicked");
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Video video = dataList.get(position);
                String videoId = video.getVideoId(); // Retrieve the ID of the clicked item
                listener.onItemClick(videoId); // Callback the listener with the item ID
            }
        }
    }
}
