package com.example.prm392_musicapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< Updated upstream
import android.widget.Filter;
import android.widget.Filterable;
=======
import android.widget.Button;
>>>>>>> Stashed changes
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_musicapp.R;
<<<<<<< Updated upstream
import com.example.prm392_musicapp.models.Item;
import com.example.prm392_musicapp.models.Music;
=======
import com.example.prm392_musicapp.fragments.MyBottonSheetFragment;
import com.example.prm392_musicapp.models.Item_model_bottom_sheet;
import com.example.prm392_musicapp.models.SearchItem;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.MusicViewHolder> {
    private List<Item> mListMusic;
    Activity activity;
<<<<<<< Updated upstream

    public SearchAdapter(List<Item> mListMusic, Activity activity) {
=======
    private OnItemClickListener listener;
    private FragmentManager fragmentManager;


    public interface OnItemClickListener {
        void onItemClick(String itemId);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SearchAdapter(List<SearchItem> mListMusic, Activity activity, FragmentManager fragmentManager) {
>>>>>>> Stashed changes
        this.mListMusic = mListMusic;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }
   public  void  setSearchList(List<Item> filteredList){
        this.mListMusic = filteredList;
       notifyDataSetChanged();
   }
    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Item music = mListMusic.get(position);
        if (music == null) {
            return;
        }
        Glide.with(holder.imgMusic.getContext())
                .load(music.getSnippet().getThumbnails().getMedium().getUrl())
                .into(holder.imgMusic);
        holder.tvName.setText(music.getSnippet().getTitle());
        holder.tvSinger.setText(music.getSnippet().getChannelTitle());
        holder.btnOpenModelSheet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Item_model_bottom_sheet> list = new ArrayList<>();
                list.add(new Item_model_bottom_sheet("Add to playlist"));
                list.add(new Item_model_bottom_sheet("Like"));
                MyBottonSheetFragment myBottonSheetFragment = new MyBottonSheetFragment(list, new IClickBottomSheetListener() {
                    @Override
                    public void clickItem(Item_model_bottom_sheet itemObject) {
                        Toast.makeText(v.getContext(), itemObject.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
                myBottonSheetFragment.show(fragmentManager, myBottonSheetFragment.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListMusic != null){
            return mListMusic.size();
        }
        return 0;
    }



    public class MusicViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgMusic;
        TextView tvName;
        TextView tvSinger;
        Button btnOpenModelSheet;
        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
<<<<<<< Updated upstream
=======
            itemView.setOnClickListener(this);
            btnOpenModelSheet = itemView.findViewById(R.id.bottomSheetButton);
>>>>>>> Stashed changes
            imgMusic = itemView.findViewById(R.id.tv_search_thumb);
            tvName = itemView.findViewById(R.id.tv_search_music);
            tvSinger = itemView.findViewById(R.id.tv_search_singer);
        }
<<<<<<< Updated upstream
=======

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                SearchItem music = mListMusic.get(position);
                String itemId = music.getId().getVideoId(); // Retrieve the ID of the clicked item
                listener.onItemClick(itemId); // Callback the listener with the item ID
            }
        }

//        private void clickOpenBottomSheetFragment(){
//            List<Item_model_bottom_sheet> list = new ArrayList<>();
//            list.add(new Item_model_bottom_sheet("Add to playlist"));
//            list.add(new Item_model_bottom_sheet("Like"));
//            MyBottonSheetFragment myBottonSheetFragment = new MyBottonSheetFragment(list, new IClickBottomSheetListener() {
//                @Override
//                public void clickItem(Item_model_bottom_sheet itemObject) {
//                    Toast.makeText(activity.getApplicationContext(), itemObject.getName(),Toast.LENGTH_SHORT).show();
//                }
//            });
//             myBottonSheetFragment.show(fragmentActivity.getSupportFragmentManager(), myBottonSheetFragment.getTag());
//        }


>>>>>>> Stashed changes
    }



    }

