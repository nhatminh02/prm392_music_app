package com.example.prm392_musicapp.api;

import com.example.prm392_musicapp.models.SearchItemDetails;
import com.example.prm392_musicapp.models.SingleItemDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetVideoDataService {
    @GET("search")
    Call<SearchItemDetails> getSearchVideoData(
            @Query("part") String part,
            @Query("q") String searchValue,
            @Query("key") String key,
            @Query("maxResults") String maxResults,
            @Query("regionCode") String regionCode,
            @Query("type") String type,
            @Query("topicId") String topicId
    );
    @GET("search")
    Call<SearchItemDetails> getSearchRelatedVideoData(
            @Query("part") String part,
            @Query("key") String key,
            @Query("maxResults") String maxResults,
            @Query("regionCode") String regionCode,
            @Query("type") String type,
            @Query("relatedToVideoId") String relatedToVideoId
    );

    @GET("videos")
    Call<SingleItemDetail> getVideoById(
            @Query("part") String part,
            @Query("id") String id,
            @Query("key") String key
    );
}
