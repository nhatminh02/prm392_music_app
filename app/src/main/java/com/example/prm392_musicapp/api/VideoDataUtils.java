package com.example.prm392_musicapp.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.prm392_musicapp.models.SearchItem;
import com.example.prm392_musicapp.models.SingleItem;
import com.example.prm392_musicapp.models.SearchItemDetails;
import com.example.prm392_musicapp.models.SingleItemDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDataUtils {
    //AIzaSyCxnM_yUk7Rw8xAQxwaYoDHan0Rx71FOQY
    //AIzaSyDca6EiCASpFVwlvWFcbjj_ykdoWCNDevk
    //AIzaSyBZDg-87in5IzNFeBo9PeRC_kFrcN4jjnE
    //AIzaSyBWbE-QBqsT8EqX35fyAQ7ZyNc3ZEAVQH4
    //AIzaSyA_4cHWZn3Su-7x2ct9LWzHEJHCri82Z84
    //AIzaSyAoBG7VU3ld5d396F9ABk9C99qKUyTnOOI
    //AIzaSyDL253YU0-HPTsBWDlCHNT1pqxc9OBYsAE
    //AIzaSyC4_5YZT1NgXspNddbi2LIsK0y0i4AmJJs
    static final String API_KEY = "AIzaSyC4_5YZT1NgXspNddbi2LIsK0y0i4AmJJs";

    public static MutableLiveData<List<SearchItem>> searchVideoData(String searchQuery) {
        MutableLiveData<List<SearchItem>> itemsLiveData = new MutableLiveData<>();

        GetVideoDataService dataService = RetrofitInstance.getRetrofit().create(GetVideoDataService.class);

        Call<SearchItemDetails> videoDetailsRequest = dataService
                .getSearchVideoData("snippet", searchQuery, API_KEY, "100", "VN", "video", "/m/04rlf");
        videoDetailsRequest.enqueue(new Callback<SearchItemDetails>() {
            @Override
            public void onResponse(Call<SearchItemDetails> call, Response<SearchItemDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<SearchItem> itemsList = response.body().getItems();
                        itemsLiveData.setValue(itemsList);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchItemDetails> call, Throwable t) {
                Log.i("failed", t.getMessage());
            }
        });

        return itemsLiveData;
    }

    public static MutableLiveData<List<SearchItem>> getRelatedVideoData(String videoId) {
        MutableLiveData<List<SearchItem>> itemsLiveData = new MutableLiveData<>();

        GetVideoDataService dataService = RetrofitInstance.getRetrofit().create(GetVideoDataService.class);
        //AIzaSyCxnM_yUk7Rw8xAQxwaYoDHan0Rx71FOQY
        //AIzaSyDca6EiCASpFVwlvWFcbjj_ykdoWCNDevk
        //AIzaSyBZDg-87in5IzNFeBo9PeRC_kFrcN4jjnE
        Call<SearchItemDetails> videoDetailsRequest = dataService
                .getSearchRelatedVideoData("snippet", API_KEY, "100", "VN", "video", videoId);
        videoDetailsRequest.enqueue(new Callback<SearchItemDetails>() {
            @Override
            public void onResponse(Call<SearchItemDetails> call, Response<SearchItemDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<SearchItem> itemsList = response.body().getItems();
                        itemsLiveData.setValue(itemsList);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchItemDetails> call, Throwable t) {
                Log.i("failed", t.getMessage());
            }
        });

        return itemsLiveData;
    }

    public static MutableLiveData<List<SingleItem>> getVideoById(String id) {
        MutableLiveData<List<SingleItem>> itemLiveData = new MutableLiveData<>();

        GetVideoDataService dataService = RetrofitInstance.getRetrofit().create(GetVideoDataService.class);
        //AIzaSyCxnM_yUk7Rw8xAQxwaYoDHan0Rx71FOQY
        //AIzaSyDca6EiCASpFVwlvWFcbjj_ykdoWCNDevk
        //AIzaSyBZDg-87in5IzNFeBo9PeRC_kFrcN4jjnE
        Call<SingleItemDetail> videoDetailsRequest = dataService
                .getVideoById("snippet", id, API_KEY);
        videoDetailsRequest.enqueue(new Callback<SingleItemDetail>() {
            @Override
            public void onResponse(Call<SingleItemDetail> call, Response<SingleItemDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<SingleItem> singleItemList = response.body().getSingleItems();
                        itemLiveData.setValue(singleItemList);
                        Log.i("response", response.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleItemDetail> call, Throwable t) {
                Log.i("failed", t.getMessage());
            }
        });

        return itemLiveData;
    }
}

//  Dùng đoạn code này trong FRAGMENT để lấy data
//  VideoDataUtils.searchVideoData([SEARCH_VALUE]).observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
//      @Override
//      public void onChanged(List<Item> items) {
//              data ở trong cái items
//      }
//  });
