package com.example.prm392_musicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.Playlist;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PLaylistHolder>{

    List<Playlist> playlists;

    public PlaylistAdapter(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistAdapter.PLaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_music,
                parent,false);
        return new PlaylistAdapter.PLaylistHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.PLaylistHolder holder, int position) {
        holder.tv_playlist_name.setText(playlists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class PLaylistHolder extends RecyclerView.ViewHolder{
        TextView tv_playlist_name;

        public PLaylistHolder(@NonNull View itemView) {
            super(itemView);
            tv_playlist_name = itemView.findViewById(R.id.tv_playlist_name);

        }
    }
}
