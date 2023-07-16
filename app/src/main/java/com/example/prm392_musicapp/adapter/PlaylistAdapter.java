package com.example.prm392_musicapp.adapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.models.Playlist;
import com.example.prm392_musicapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PLaylistHolder> {

    List<Playlist> playlists;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    private OnClickListener mListener;


    public interface OnClickListener {
        void onClick(int position, Video model);
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

    public class PLaylistHolder extends RecyclerView.ViewHolder {
        TextView tv_playlist_name;
        private OnClickListener onClickListener;

        public PLaylistHolder(@NonNull View itemView) {
            super(itemView);
            tv_playlist_name = itemView.findViewById(R.id.tv_playlist_name);

            ((FloatingActionButton) itemView.findViewById(R.id.delete_playlist)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    mySQLiteOpenHelper = new MySQLiteOpenHelper(itemView.getContext(), "ProjectDB", null, 1);
                    SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
                    db = mySQLiteOpenHelper.getWritableDatabase();
                    db.delete("Playlists", "PLName=?", new String[]{tv_playlist_name.getText().toString()});
                    db.close();

                    playlists.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

    }
}
