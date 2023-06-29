package com.example.prm392_musicapp.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.prm392_musicapp.models.Item;
import com.example.prm392_musicapp.models.VideoDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDataUtils {
    static List<Item> itemsList;

    public static MutableLiveData<List<Item>> searchVideoData(String searchQuery) {
        MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();

        GetVideoDataService dataService = RetrofitInstance.getRetrofit().create(GetVideoDataService.class);
        Call<VideoDetails> videoDetailsRequest = dataService
                .getSearchVideoData("snippet", searchQuery, "AIzaSyDca6EiCASpFVwlvWFcbjj_ykdoWCNDevk", "100", "VN");
        videoDetailsRequest.enqueue(new Callback<VideoDetails>() {
            @Override
            public void onResponse(Call<VideoDetails> call, Response<VideoDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Item> itemsList = response.body().getItems();
                        itemsLiveData.setValue(itemsList);
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoDetails> call, Throwable t) {
                Log.i("failed", t.getMessage());
            }
        });

        return itemsLiveData;
    }
}

//  Dùng đoạn code này trong FRAGMENT để lấy data
//  VideoDataUtils.searchVideoData([SEARCH_VALUE]).observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
//      @Override
//      public void onChanged(List<Item> items) {
//              data ở trong cái items
//      }
//  });
