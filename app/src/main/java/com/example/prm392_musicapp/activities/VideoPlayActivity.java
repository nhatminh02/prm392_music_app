package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.prm392_musicapp.R;
import com.example.prm392_musicapp.SQLite.MySQLiteOpenHelper;
import com.example.prm392_musicapp.api.VideoDataUtils;
import com.example.prm392_musicapp.models.Thumbnails;
import com.example.prm392_musicapp.models.Id;
import com.example.prm392_musicapp.models.SingleItem;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBarListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoPlayActivity extends AppCompatActivity {
    private TextView tv_title;
    private String thumbnails;
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
    MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_page);
        Log.i("on1", "onCreate");


        tv_title = findViewById(R.id.tv_title);
        tv_channel = findViewById(R.id.tv_channel);

        heart = findViewById(R.id.imv_heart_icon);
        emptyHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_empty);
        fillHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_fill);

        //volume control bar
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

        //get video attribute
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        itemId = getIntent().getStringExtra("itemId");
        Id id = new Id();

        VideoDataUtils.getVideoById(itemId).observe(this, new Observer<List<SingleItem>>() {
            @Override
            public void onChanged(List<SingleItem> singleItems) {
                tv_title.setText(singleItems.get(0).getSnippet().getTitle());
                tv_channel.setText(singleItems.get(0).getSnippet().getChannelTitle());
                thumbnails = singleItems.get(0).getSnippet().getThumbnails().getMedium().getUrl();
            }
        });

        //add liked track
        //todo: check xem track da ton tai trong bang liked track hay chua, neu ton tai thi set heart thanh fillheart
        // trong cai ben duoi co ve dung nhung no k chay
        openHelper = new MySQLiteOpenHelper(this, "ProjectDB", null, 1);
        db = openHelper.getReadableDatabase();
        String sql = "select * from LikedTracks";
        Cursor c = db.rawQuery(sql, null);
        heart.setImageDrawable(emptyHeart);
        while (c.moveToNext()) {
            String vId = c.getString(1);
            if (vId.equals(itemId)) {
                heart.setImageDrawable(fillHeart);
                break;
            }
        }

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatedVectorDrawable drawable = full ? emptyHeart : fillHeart;
                if (drawable == emptyHeart) {
                    db = openHelper.getWritableDatabase();
                    db.delete("LikedTracks", "videoId=?", new String[]{itemId});
                    db.close();
                } else {
                    db = openHelper.getWritableDatabase();
                    String sql = "insert into LikedTracks(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                    db.execSQL(sql, new String[]{itemId, tv_title.getText().toString(), thumbnails, tv_channel.getText().toString()});
                    db.close();
                }
                heart.setImageDrawable(drawable);
                drawable.start();
                full = !full;
            }
        });

        //video play
        youTubePlayerView.enableBackgroundPlayback(true);
        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                defaultPlayerUiController.showUi(false);

                //thanh chỉnh thời gian chạy của video
                YouTubePlayerSeekBar youTubePlayerSeekBar = findViewById(R.id.youtube_player_seekbar);
                youTubePlayerSeekBar.getVideoCurrentTimeTextView()
                        .setTextColor(ContextCompat.getColor(VideoPlayActivity.this, android.R.color.black));
                youTubePlayerSeekBar.setYoutubePlayerSeekBarListener(new YouTubePlayerSeekBarListener() {
                    @Override
                    public void seekTo(float time) {
                        youTubePlayer.seekTo(time);
                    }
                });
                youTubePlayer.addListener(youTubePlayerSeekBar);

                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());

                youTubePlayer.loadVideo(itemId, 0);

                //video control button
                ImageView videoControl = findViewById(R.id.videoControl);
                checkControl = true;
                videoControl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkControl) {
                            youTubePlayer.pause();
                            videoControl.setImageResource(R.drawable.baseline_play_arrow_24_dark);
                            checkControl = !checkControl;
                        } else {
                            youTubePlayer.play();
                            videoControl.setImageResource(R.drawable.baseline_pause_24_dark);
                            checkControl = !checkControl;
                        }
                    }
                });

                //suffle button
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

                //repeat button
                ImageView repeat = findViewById(R.id.imv_repeat);
                checkRepeat = false;
                repeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkRepeat) {
                            //todo: tat chuc nang repeat
                            repeat.setImageResource(R.drawable.baseline_repeat_24);
                            checkRepeat = !checkRepeat;
                        } else {
                            //todo: bat chuc nang repeat
                            repeat.setImageResource(R.drawable.baseline_repeat_on_24);
                            checkRepeat = !checkRepeat;
                        }
                    }
                });
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);

                //xử lý chức năng repeat
                if (state.equals(PlayerConstants.PlayerState.ENDED) && checkRepeat) {
                    youTubePlayer.loadVideo(itemId, 0);
                }
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).fullscreen(1).build();
        youTubePlayerView.initialize(listener, options);
    }

    public void onBack(View view) {
        videoPage = findViewById(R.id.layout_video_play);
        Intent intent = new Intent(VideoPlayActivity.this, MainActivity.class);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        videoPage.startAnimation(slideDown);
        moveTaskToBack(true);
        startActivity(intent);
    }
}