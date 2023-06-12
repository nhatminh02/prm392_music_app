package com.example.prm392_musicapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FuncAdapter extends RecyclerView.Adapter<FuncAdapter.FuncHolder> {

    List<LibFunc> f;

    public FuncAdapter(List<LibFunc> f) {
        this.f = f;
    }

    @NonNull
    @Override
    public FuncAdapter.FuncHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_libfunc, parent, false);
        return new FuncHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncAdapter.FuncHolder holder, int position) {
        holder.des.setText(f.get(position).getDes());
        holder.btn.setText(f.get(position).getBtn());

    }

    @Override
    public int getItemCount() {
        return f.size();
    }

    public class FuncHolder extends RecyclerView.ViewHolder{

        TextView des;
        TextView btn;

        public FuncHolder(@NonNull View itemView) {
            super(itemView);
            des = itemView.findViewById(R.id.des);
            btn = itemView.findViewById(R.id.btn);
        }
    }
}
