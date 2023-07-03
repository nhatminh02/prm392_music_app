package com.example.prm392_musicapp.api;

import com.example.prm392_musicapp.models.VideoDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetVideoDataService {
    @GET("search")
    Call<VideoDetails> getSearchVideoData(
            @Query("part") String part,
            @Query("q") String searchValue,
            @Query("key") String key,
            @Query("maxResults") String maxResults,
            @Query("regionCode") String regionCode,
            @Query("type") String type,
            @Query("topicId") String topicId
    );
}
