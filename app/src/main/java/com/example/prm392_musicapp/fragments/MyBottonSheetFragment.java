package com.example.prm392_musicapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.adapter.BottomSheetAdapter;
import com.example.prm392_musicapp.adapter.IClickBottomSheetListener;
import com.example.prm392_musicapp.models.Item_model_bottom_sheet;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class MyBottonSheetFragment extends BottomSheetDialogFragment {
    private List<Item_model_bottom_sheet> mListItem;
    private IClickBottomSheetListener IClickListener;

    public MyBottonSheetFragment(List<Item_model_bottom_sheet> mListItem, IClickBottomSheetListener IClickListener) {
        this.mListItem = mListItem;
        this.IClickListener = IClickListener;
    }


    @NonNull
    @Override//Hiển thi  bẳng bottom sheet.
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_button_sheet,null);
        bottomSheetDialog.setContentView(view);
        // get rcv của model sheet.
        RecyclerView revMusic = view.findViewById(R.id.rev_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        revMusic.setLayoutManager(linearLayoutManager);

        BottomSheetAdapter itemAdapter = new BottomSheetAdapter(mListItem, new IClickBottomSheetListener() {
            @Override
            public void clickItem(Item_model_bottom_sheet itemObject) {
                IClickListener.clickItem(itemObject);
            }
        });
        //set addapter cho recycler view của model button sheet.
        revMusic.setAdapter(itemAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        revMusic.addItemDecoration(itemDecoration);
        return  bottomSheetDialog;
    }
}
