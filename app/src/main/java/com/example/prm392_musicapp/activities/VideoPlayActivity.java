package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class VideoPlayActivity extends YouTubeBaseActivity implements LifecycleOwner{
    String title,channel;
    private TextView tv_title;
    private TextView tv_channel;
    private ImageView heart;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    private boolean full = false;
    private LifecycleRegistry lifecycleRegistry;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_page);

        tv_title = findViewById(R.id.tv_title);
        tv_channel = findViewById(R.id.tv_channel);

        heart = findViewById(R.id.imv_heart_icon);
        emptyHeart = (AnimatedVectorDrawable)getDrawable(R.drawable.avd_heart_empty);
        fillHeart = (AnimatedVectorDrawable)getDrawable(R.drawable.avd_heart_fill);

        heart.setImageDrawable(emptyHeart);

        YouTubePlayerView youTubePlayerView;
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        lifecycleRegistry = new LifecycleRegistry(this);
        LifecycleOwner lifecycleOwner = VideoPlayActivity.this;
        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                VideoDataUtils.searchVideoData("noi nay co anh").observe(lifecycleOwner, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(List<Item> items) {
                        youTubePlayer.loadVideo("FN7ALfpGxiI");
                        title = items.get(0).getSnippet().getTitle();
                        channel = items.get(0).getSnippet().getChannelTitle();
                    }
                });
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Toast.makeText(,"Failed",Toast.LENGTH_SHORT).show();
            }
        };
        youTubePlayerView.initialize("AIzaSyDca6EiCASpFVwlvWFcbjj_ykdoWCNDevk", listener);


        tv_title.setText(title);
        tv_channel.setText(channel);


    }
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    public void animate(View view)
    {
        AnimatedVectorDrawable drawable
                = full
                ? emptyHeart
                : fillHeart;
        heart.setImageDrawable(drawable);
        drawable.start();
        full = !full;
    }

}