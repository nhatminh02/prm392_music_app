package com.example.prm392_musicapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.prm392_musicapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

//class này để custom nút của mình (cái này t bê nguyên trên github của họ nên mới nhiều chứ app mình cũng k dùng nhiều thế như này)
class CustomPlayerUiController extends AbstractYouTubePlayerListener {
    private final YouTubePlayerTracker playerTracker;
    private final Context context;
    private final YouTubePlayer youTubePlayer;
    private final YouTubePlayerView youTubePlayerView;
    // panel is used to intercept clicks on the WebView, I don't want the user to be able to click the WebView directly.
    private View panel;
    private boolean fullscreen = false;
    private Handler handler;

    CustomPlayerUiController(Context context, View customPlayerUi, YouTubePlayer youTubePlayer, YouTubePlayerView youTubePlayerView) {
        this.context = context;
        this.youTubePlayer = youTubePlayer;
        this.youTubePlayerView = youTubePlayerView;

        playerTracker = new YouTubePlayerTracker();
        youTubePlayer.addListener(playerTracker);

        initViews(customPlayerUi);
    }

    private void initViews(View playerUi) {
        boolean check = false;
        panel = playerUi.findViewById(R.id.panel);
        ImageView plause = playerUi.findViewById(R.id.imv_plause);
        ImageView fullScreen = playerUi.findViewById(R.id.imv_fullscreen);
        panel.setOnClickListener((view) -> {
            plause.setVisibility(View.VISIBLE);
            plause.postDelayed(new Runnable() {
                public void run() {
                    plause.setVisibility(View.GONE);
                }
            }, 5000);

            fullScreen.setVisibility(View.VISIBLE);
            fullScreen.postDelayed(new Runnable() {
                public void run() {
                    fullScreen.setVisibility(View.GONE);
                }
            }, 5000);
        });

        plause.setOnClickListener((view) -> {
            if (playerTracker.getState() == PlayerConstants.PlayerState.PLAYING) {
                youTubePlayer.pause();
                plause.setImageResource(R.drawable.baseline_play_arrow_24);
            } else {
                youTubePlayer.play();
                plause.setImageResource(R.drawable.baseline_pause_24);
            }
        });

        fullScreen.setOnClickListener((view) -> {
            if (fullscreen) youTubePlayerView.wrapContent();
            else youTubePlayerView.matchParent();

            fullscreen = !fullscreen;
        });
    }

    @Override
    public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
        if (state == PlayerConstants.PlayerState.PLAYING || state == PlayerConstants.PlayerState.PAUSED || state == PlayerConstants.PlayerState.VIDEO_CUED)
            panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        else if (state == PlayerConstants.PlayerState.BUFFERING)
            panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
    }

}
