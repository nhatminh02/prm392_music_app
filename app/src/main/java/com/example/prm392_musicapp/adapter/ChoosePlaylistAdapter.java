package com.example.prm392_musicapp.adapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private OnItemClickListener mListener;
    private int position;


    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void onClick(int position, Video model);
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

    public class PLaylistHolder extends RecyclerView.ViewHolder {
        TextView tv_playlist_name;
        private OnClickListener onClickListener;

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
                        Cursor cursor = db.rawQuery("SELECT LAST PLMid FROM PlaylistMusic", null);
                        while (cursor.moveToNext()) {
                            id = Integer.parseInt(String.valueOf(cursor.getInt(0)));
                        }
                        cursor.close();
                        String sql = "INSERT into PLaylistMus(PLMid,PLid) VALUES (?,?)";
                        db.execSQL(sql, new String[]{String.valueOf(id), String.valueOf(position+1)});
                        db.close();
                    }
                }
            });

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
