package com.example.prm392_musicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.models.ItemOption;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    private List<ItemOption> optionList;
    public OptionAdapter(@NonNull List<ItemOption> optionList) {

        this.optionList = optionList;
    }

    @NonNull
    @Override

    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        return new OptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        ItemOption itemOption = optionList.get(position);
        holder.textView.setText(itemOption.getText());
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_item);
        }

    }
}
