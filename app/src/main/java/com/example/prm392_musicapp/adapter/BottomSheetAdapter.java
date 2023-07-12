package com.example.prm392_musicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.Item_model_bottom_sheet;

import java.util.List;

public class BottomSheetAdapter  extends RecyclerView.Adapter<BottomSheetAdapter.ItemViewHolder> {
    private List<Item_model_bottom_sheet> mListItem;
    private IClickBottomSheetListener iBottomSheetListener;

    public BottomSheetAdapter(List<Item_model_bottom_sheet> mListItem, IClickBottomSheetListener iClickBottomSheetListener) {
        this.mListItem = mListItem;
        this.iBottomSheetListener = iClickBottomSheetListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_bottom_layout,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
       final Item_model_bottom_sheet itemObject = mListItem.get(position);
        if(itemObject == null){
            return;
        }
        holder.tvItem.setText(itemObject.getName());
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBottomSheetListener.clickItem(itemObject);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListItem != null){
            return mListItem.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }

}
