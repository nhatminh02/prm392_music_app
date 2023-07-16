package com.example.prm392_musicapp.adapter;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LikedMusicAdapter extends RecyclerView.Adapter<LikedMusicAdapter.MusicHolder> {
    private final List<Video> dataList;
    String videoId;
    private LikedMusicAdapter.OnItemClickListener listener;
    MySQLiteOpenHelper mySQLiteOpenHelper;

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(LikedMusicAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

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

        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            Log.i("LikedMusicAdapter", "Item clicked");
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Video video = dataList.get(position);
                String videoId = video.getVideoId(); // Retrieve the ID of the clicked item
                listener.onItemClick(videoId); // Callback the listener with the item ID
            }
        }
    }
}
