package com.example.prm392_musicapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.SearchItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MusicViewHolder> {
    private List<SearchItem> mListMusic;
    Activity activity;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SearchAdapter(List<SearchItem> mListMusic, Activity activity) {
        this.mListMusic = mListMusic;
        this.activity = activity;
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
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
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

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgMusic = itemView.findViewById(R.id.tv_search_thumb);
            tvName = itemView.findViewById(R.id.tv_search_music);
            tvSinger = itemView.findViewById(R.id.tv_search_singer);
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


}

