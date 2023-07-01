package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoPlayActivity extends AppCompatActivity {
    String videoId = "FN7ALfpGxiI";
    private TextView tv_title;
    private TextView tv_channel;
    private ImageView heart;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    private YouTubePlayerView youTubePlayerView;
    private boolean full = false;
    ConstraintLayout cL_top, videoPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_page);

        tv_title = findViewById(R.id.tv_title);
        tv_channel = findViewById(R.id.tv_channel);

        heart = findViewById(R.id.imv_heart_icon);
        emptyHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_empty);
        fillHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_fill);

        heart.setImageDrawable(emptyHeart);


        youTubePlayerView = findViewById(R.id.youtube_player_view);
        //toDo : cứuuuuuuuuuuuuuuuuuuuuuuuu
//        VideoDataUtils.searchVideoData("Noi nay co anh mv").
//                observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
//                    @Override
//                    public void onChanged(List<Item> items) {
//                        videoId = items.get(0).getId().getVideoId();
//                        tv_title.setText(items.get(0).getSnippet().getTitle());
//                        tv_channel.setText(items.get(0).getSnippet().getChannelTitle());
//                    }
//                });

        //giữ cho video chạy khi out app hoặc đóng màn hình
        youTubePlayerView.enableBackgroundPlayback(true);

        View videoPlayer = youTubePlayerView.inflateCustomPlayerUi(R.layout.video_page);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                CustomPlayerUiController customPlayerUiController =
                        new CustomPlayerUiController(VideoPlayActivity.this, videoPlayer, youTubePlayer, youTubePlayerView);

                youTubePlayer.addListener(customPlayerUiController);

                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        videoId, 0f
                );
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();

        youTubePlayerView.initialize(listener, options);


    }

    private LifecycleOwner getViewLifecycleOwner() {
        return null;
    }

    //heart icon
    public void animate(View view) {
        AnimatedVectorDrawable drawable
                = full
                ? emptyHeart
                : fillHeart;
        heart.setImageDrawable(drawable);
        drawable.start();
        full = !full;
    }


    public void onBack(View view) {
        videoPage = findViewById(R.id.layout_video_play);
        Intent intent = new Intent(VideoPlayActivity.this,MainActivity.class);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        videoPage.startAnimation(slideDown);
        startActivity(intent);
    }
}