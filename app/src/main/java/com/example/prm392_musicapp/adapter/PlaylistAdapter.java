package com.example.prm392_musicapp.adapter;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.fragments.FragmentChoosePlaylist;
import com.example.prm392_musicapp.fragments.FragmentPlaylistDetail;
import com.example.prm392_musicapp.models.Playlist;
import com.example.prm392_musicapp.models.SearchItem;
import com.example.prm392_musicapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PLaylistHolder> {

    List<Playlist> playlists;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentPlaylistDetail fragmentPlaylistDetail;
    private OnClickListener onClickListener;


    public interface OnClickListener {
        void onClick(int position);
    }
    public void setOnItemClickListener(PlaylistAdapter.OnClickListener listener) {
        this.onClickListener = listener;
    }


    public PlaylistAdapter(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistAdapter.PLaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_playlist,
                parent, false);
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

    public class PLaylistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_playlist_name;


        public PLaylistHolder(@NonNull View itemView) {
            super(itemView);
            tv_playlist_name = itemView.findViewById(R.id.tv_playlist_name);
            itemView.setOnClickListener(this);


            ((ImageView) itemView.findViewById(R.id.delete_playlist)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    mySQLiteOpenHelper = new MySQLiteOpenHelper(itemView.getContext(), "ProjectDB", null, 1);
                    SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
                    db = mySQLiteOpenHelper.getWritableDatabase();
                    db.delete("PLaylistMus", "PLid=?", new String[]{String.valueOf(playlists.get(position).getId())});
                    db.delete("Playlists", "PLid=?", new String[]{String.valueOf(playlists.get(position).getId())});
                    db.close();
                    Toast.makeText(itemView.getContext(), "Deleted playlist successfully", Toast.LENGTH_SHORT).show();

                    playlists.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onClickListener != null) {
                Playlist playlist = playlists.get(position);
                int itemId = playlist.getId(); // Retrieve the ID of the clicked item
                onClickListener.onClick(itemId); // Callback the listener with the item ID
            }
        }
    }
}
