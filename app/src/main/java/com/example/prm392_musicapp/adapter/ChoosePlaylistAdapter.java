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
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.models.Playlist;
import com.example.prm392_musicapp.models.SearchItem;
import com.example.prm392_musicapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ChoosePlaylistAdapter extends RecyclerView.Adapter<ChoosePlaylistAdapter.PLaylistHolder> {

    List<Playlist> playlists;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    private SearchAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(SearchAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public ChoosePlaylistAdapter(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public ChoosePlaylistAdapter.PLaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_playlist,
                parent, false);
        return new ChoosePlaylistAdapter.PLaylistHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoosePlaylistAdapter.PLaylistHolder holder, int position) {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("sdfghj", String.valueOf(getBindingAdapterPosition()));
                    int id = 0;
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Playlist playlist = playlists.get(position);
                        int itemId = playlist.getId(); // Retrieve the ID of the clicked item
                        mySQLiteOpenHelper = new MySQLiteOpenHelper(itemView.getContext(), "ProjectDB", null, 1);
                        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
                        db = mySQLiteOpenHelper.getWritableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM PLaylistMusic ORDER BY PLMid DESC LIMIT 1", null);
                        while (cursor.moveToNext()) {
                            id = Integer.parseInt(String.valueOf(cursor.getInt(0)));
                        }
                        cursor.close();

                        Cursor c = db.rawQuery("select * from PlaylistMus where PLMid = ? and PLid = ?", new String[]{String.valueOf(id), String.valueOf(position + 1)});
                        boolean exist = c.moveToFirst();
                        if (!exist) {
                            Toast.makeText(itemView.getContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
                            String sql = "INSERT into PLaylistMus(PLMid,PLid) VALUES (?,?)";
                            db.execSQL(sql, new String[]{String.valueOf(id), String.valueOf(position + 1)});
                            db.close();
                        }
                        Toast.makeText(itemView.getContext(), "This song already add to this playlist", Toast.LENGTH_SHORT).show();

                    }
                }
            });

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
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Playlist playlist = playlists.get(position);
                int itemId = playlist.getId(); // Retrieve the ID of the clicked item
                listener.onItemClick(String.valueOf(itemId)); // Callback the listener with the item ID
            }
        }
    }
}
