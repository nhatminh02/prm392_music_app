package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class TestVideoPlay extends YouTubeBaseActivity implements LifecycleOwner{
    private LifecycleRegistry lifecycleRegistry;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_video_play);

        YouTubePlayerView youTubePlayerView;
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        lifecycleRegistry = new LifecycleRegistry(this);
        LifecycleOwner lifecycleOwner = TestVideoPlay.this;
        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                VideoDataUtils.searchVideoData("noi nay co anh").observe(lifecycleOwner, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(List<Item> items) {
                        youTubePlayer.loadVideo(items.get(0).getId().getVideoId());
                    }
                });
//                youTubePlayer.setPlayerStateChangeListener(TestVideoPlay.this);
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Toast.makeText( ,"Failed",Toast.LENGTH_SHORT).show();
            }
        };
        youTubePlayerView.initialize("AIzaSyDca6EiCASpFVwlvWFcbjj_ykdoWCNDevk", listener);

    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }


//    @Override
//    public void onLoading() {
//        Log.i("state", "onLoading");
//    }
//
//    @Override
//    public void onLoaded(String s) {
//        Log.i("state", "onLoaded");
//
//    }
//
//    @Override
//    public void onAdStarted() {
//        Log.i("state", "onAdStarted");
//
//    }
//
//    @Override
//    public void onVideoStarted() {
//        Log.i("state", "onVideoStarted");
//    }
//
//    @Override
//    public void onVideoEnded() {
//        Log.i("state", "onVideoEnded");
//
//    }
//
//    @Override
//    public void onError(YouTubePlayer.ErrorReason errorReason) {
//        Log.i("state", "onError");
//
//    }
}