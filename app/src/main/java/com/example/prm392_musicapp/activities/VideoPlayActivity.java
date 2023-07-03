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

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
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
    String videoId = "";
    private TextView tv_title;
    private TextView tv_channel;
    private ImageView heart;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    private YouTubePlayerView youTubePlayerView;
    private boolean full = false;
    private ConstraintLayout videoPage;
    private SeekBar seekBar;
    private AudioManager audioManager;
    private boolean checkControl, checkSuffle, checkRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_page);

        tv_title = findViewById(R.id.tv_title);
        tv_channel = findViewById(R.id.tv_channel);

        heart = findViewById(R.id.imv_heart_icon);
        emptyHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_empty);
        fillHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_fill);

        seekBar = findViewById(R.id.volumeSeekBar);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int max_volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setMax(max_volume);
        int current_volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(current_volume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        heart.setImageDrawable(emptyHeart);


        youTubePlayerView = findViewById(R.id.youtube_player_view);
        VideoDataUtils.searchVideoData("chung ta khong thuoc ve nhau").observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                videoId = items.get(0).getId().getVideoId();
                tv_title.setText(items.get(0).getSnippet().getTitle());
                tv_channel.setText(items.get(0).getSnippet().getChannelTitle());
            }
        });

        youTubePlayerView.enableBackgroundPlayback(true);

        View videoPlayer = youTubePlayerView.inflateCustomPlayerUi(R.layout.video_page);
        ImageView plause = videoPlayer.findViewById(R.id.imv_plause);


        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(VideoPlayActivity.this, videoPlayer, youTubePlayer, youTubePlayerView);

                youTubePlayer.addListener(customPlayerUiController);

                youTubePlayer.loadVideo(videoId, 0);

                ImageView volumeControl = findViewById(R.id.imv_volumeControl);
                checkControl = true;
                volumeControl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkControl) {
                            youTubePlayer.pause();
                            plause.setImageResource(R.drawable.baseline_play_arrow_24);
                            volumeControl.setImageResource(R.drawable.baseline_play_arrow_24_dark);
                            checkControl = !checkControl;
                        } else {
                            youTubePlayer.play();
                            plause.setImageResource(R.drawable.baseline_pause_24);
                            volumeControl.setImageResource(R.drawable.baseline_pause_24_dark);
                            checkControl = !checkControl;
                        }
                    }
                });

                ImageView suffle = findViewById(R.id.imv_suffle);
                checkSuffle = false;
                suffle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkSuffle) {
                            //todo: tat chuc nang suffle
                            suffle.setImageResource(R.drawable.baseline_shuffle_24);
                            checkSuffle = !checkSuffle;
                        } else {
                            //todo: bat chuc nang suffle
                            suffle.setImageResource(R.drawable.baseline_shuffle_on_24);
                            checkSuffle = !checkSuffle;
                        }
                    }
                });

                ImageView repeat = findViewById(R.id.imv_repeat);
                checkRepeat = false;
                repeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkRepeat) {
                            //todo: tat chuc nang repeat
                            repeat.setImageResource(R.drawable.baseline_repeat_24);
                            checkRepeat = ! checkRepeat;
                        } else {
                            //todo: bat chuc nang repeat
                            repeat.setImageResource(R.drawable.baseline_repeat_on_24);
                            checkRepeat = ! checkRepeat;
                        }
                    }
                });
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();

        youTubePlayerView.initialize(listener, options);


    }

    //heart icon
    public void animateIcon(View view) {
        AnimatedVectorDrawable drawable = full ? emptyHeart : fillHeart;
        heart.setImageDrawable(drawable);
        drawable.start();
        full = !full;
    }

    public void onBack(View view) {
        videoPage = findViewById(R.id.layout_video_play);
        Intent intent = new Intent(VideoPlayActivity.this, MainActivity.class);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        videoPage.startAnimation(slideDown);
        startActivity(intent);
    }
}