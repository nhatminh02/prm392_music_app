package com.example.prm392_musicapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.models.ItemOption;
import com.example.prm392_musicapp.models.SearchItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MusicViewHolder> {
    private List<SearchItem> mListMusic;
    Activity activity;
    private OnItemClickListener listener;
    private BottomSheetDialog bottomSheetDialog;
    MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    public interface OnOptionClickListener {
        void onOptionClick(int position, SearchItem music);
    }

    private OnOptionClickListener optionListener;

    public void setOnOptionClickListener(OnOptionClickListener listener) {
        this.optionListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SearchAdapter(List<SearchItem> mListMusic, Activity activity) {
        this.mListMusic = mListMusic;
        this.activity = activity;
        bottomSheetDialog = new BottomSheetDialog(activity);
        openHelper = new MySQLiteOpenHelper(activity, "ProjectDB", null, 1);
    }

    public void setSearchList(List<SearchItem> filteredList) {
        this.mListMusic = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SearchItem music = mListMusic.get(position);
        if (music == null) {
            return;
        }
        Glide.with(holder.imgMusic.getContext())
                .load(music.getSnippet().getThumbnails().getMedium().getUrl())
                .into(holder.imgMusic);
        //tiêu đề mà dài quá thì cắt bớt thay phần còn lại thành "..."
        if (music.getSnippet().getTitle().trim().length() > 30) {
            holder.tvName.setText(music.getSnippet().getTitle().substring(0, 27) + "...");
        } else {
            holder.tvName.setText(music.getSnippet().getTitle());
        }
        holder.tvSinger.setText(music.getSnippet().getChannelTitle());
        holder.buttonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtonSheet(position, music);
            }
        });
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        if (mListMusic != null) {
            return mListMusic.size();
        }
        return 0;
    }


    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imgMusic;
        TextView tvName;
        TextView tvSinger;
        ImageView buttonOption;
        private int position;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgMusic = itemView.findViewById(R.id.tv_search_thumb);
            tvName = itemView.findViewById(R.id.tv_search_music);
            tvSinger = itemView.findViewById(R.id.tv_search_singer);
            buttonOption=itemView.findViewById(R.id.button_option);
            buttonOption.setOnClickListener(this);
        }
        public void setPosition(int position) {
            this.position = position;
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                SearchItem music = mListMusic.get(position);
                String itemId = music.getId().getVideoId(); // Retrieve the ID of the clicked item
                listener.onItemClick(itemId); // Callback the listener with the item ID
            }
        }

    }

    private void showButtonSheet(int position, SearchItem music){
        View bottomSheetView = LayoutInflater.from(activity).inflate(R.layout.layout_bottom_sheet,null);
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.rev_data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView        .setLayoutManager(layoutManager);

        List<ItemOption> listOption = new ArrayList<>();
        listOption.add(new ItemOption("Add to playlist"));
        listOption.add(new ItemOption("Like"));

        OptionAdapter optionAdapter = new OptionAdapter(listOption);
        recyclerView.setAdapter(optionAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecoration);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        optionAdapter.setOnItemClickListener(new OptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (optionListener != null) {
                    optionListener.onOptionClick(position, music);
                }
            }
        });
    }
}



