package com.example.prm392_musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.prm392_musicapp.models.SearchItem;
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
import java.util.Random;

public class VideoPlayActivity extends AppCompatActivity {
    private TextView tv_title;
    private String thumbnails;
    private TextView tv_channel;
    private ImageView heart;
    private ImageView volumeUp;
    private ImageView volumeDown;
    private ImageView videoControl;
    private ImageView skipNext;
    private ImageView skipPrev;
    private ImageView repeat;
    private ImageView suffle;
    private ImageView backBtn;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer player;
    private boolean full = false;
    private ConstraintLayout videoPage;
    private SeekBar seekBar;
    private AudioManager audioManager;
    private boolean checkControl, checkSuffle, checkRepeat;

    private boolean darkMode;
    private SharedPreferences sharedPreferences;
    private MySQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private String itemId, title, thumbnail, channel;
    private int currentVideoIndex = 0;
    private String currentId;
    private String prevId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_page);

        SharedPreferences sharedPreferencesVideoDetail = getSharedPreferences("VideoDetailSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefVideoDetailEdit = sharedPreferencesVideoDetail.edit();

        tv_title = findViewById(R.id.tv_title);
        tv_channel = findViewById(R.id.tv_channel);
        skipNext = findViewById(R.id.imv_next);
        skipPrev = findViewById(R.id.imv_previous);
        videoControl = findViewById(R.id.videoControl);
        repeat = findViewById(R.id.imv_repeat);
        suffle = findViewById(R.id.imv_suffle);
        backBtn = findViewById(R.id.imv_back);
        volumeUp = findViewById(R.id.volume_up);
        volumeDown = findViewById(R.id.volume_down);

        sharedPreferences = getSharedPreferences("mode", Context.MODE_PRIVATE);
        darkMode = sharedPreferences.getBoolean("dark", false);
        if (darkMode) {
            skipNext.setImageResource(R.drawable.baseline_skip_next_24_light);
            skipPrev.setImageResource(R.drawable.baseline_skip_previous_24_light);
            videoControl.setImageResource(R.drawable.baseline_pause_24);
            repeat.setImageResource(R.drawable.baseline_repeat_24_light);
            suffle.setImageResource(R.drawable.baseline_shuffle_24_light);
            backBtn.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_light);
            volumeUp.setImageResource(R.drawable.baseline_volume_up_24_light);
            volumeDown.setImageResource(R.drawable.baseline_volume_down_24_light);
        } else {
            skipNext.setImageResource(R.drawable.baseline_skip_next_24_dark);
            skipPrev.setImageResource(R.drawable.baseline_skip_previous_24_dark);
            videoControl.setImageResource(R.drawable.baseline_pause_24_dark);
            repeat.setImageResource(R.drawable.baseline_repeat_24);
            suffle.setImageResource(R.drawable.baseline_shuffle_24);
            backBtn.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_dark);
            volumeUp.setImageResource(R.drawable.baseline_volume_up_24);
            volumeDown.setImageResource(R.drawable.baseline_volume_down_24);
        }


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

        VideoDataUtils.getVideoById(itemId).observe(this, new Observer<List<SingleItem>>() {
            @Override
            public void onChanged(List<SingleItem> singleItems) {
                tv_title.setText(singleItems.get(0).getSnippet().getTitle());
                tv_channel.setText(singleItems.get(0).getSnippet().getChannelTitle());
                thumbnails = singleItems.get(0).getSnippet().getThumbnails().getMedium().getUrl();

                sharedPrefVideoDetailEdit.putString("title", singleItems.get(0).getSnippet().getTitle());
                sharedPrefVideoDetailEdit.putString("channelTitle", singleItems.get(0).getSnippet().getChannelTitle());
                sharedPrefVideoDetailEdit.apply();
            }
        });

        //add liked track
        heart = findViewById(R.id.imv_heart_icon);
        emptyHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_empty); //empty heart clip
        fillHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_fill); //fill heart clip
        openHelper = new MySQLiteOpenHelper(this, "ProjectDB", null, 1);
        db = openHelper.getReadableDatabase();
        String sql = "select * from LikedTracks where videoId = ?";
        String[] selectionArgs = {itemId};
        Cursor c = db.rawQuery(sql, selectionArgs);
        boolean likedVideo = c.moveToFirst();
        Log.d("likedVideo", String.valueOf(likedVideo));
        c.close();
        if (likedVideo) {
            heart.setImageDrawable(fillHeart);
            fillHeart.start();
            full = true;
        } else {
            heart.setImageDrawable(emptyHeart);
            emptyHeart.start();
            full = false;
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

        //add recommend list
        VideoDataUtils.getRelatedVideoData(itemId).observe(this, new Observer<List<SearchItem>>() {
            @Override
            public void onChanged(List<SearchItem> searchItems) {
                String recommendId;
                Random random = new Random();
                int randomVideoIndex = random.nextInt(searchItems.size()) / 10;
                recommendId = searchItems.get(randomVideoIndex).getId().getVideoId();
                title = searchItems.get(randomVideoIndex).getSnippet().getTitle();
                thumbnail = searchItems.get(randomVideoIndex).getSnippet().getThumbnails().getMedium().getUrl();
                channel = searchItems.get(randomVideoIndex).getSnippet().getChannelTitle();
                db = openHelper.getWritableDatabase();
                db.delete("Recommends", "videoId=?", new String[]{recommendId});
                String sql = "insert into Recommends(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                db.execSQL(sql, new String[]{recommendId, title, thumbnail, channel});
                sql = "SELECT COUNT(*) FROM Recommends";
                Cursor c = db.rawQuery(sql, null);
                int count = 0;
                if (c != null && c.moveToFirst()) {
                    count = c.getInt(0);
                    c.close();
                }
                if (count > 10) {
                    String tableName = "Recommends";
                    String whereClause = "recommendId = (SELECT MIN(recommendId) FROM " + tableName + ")";
                    db.delete(tableName, whereClause, null);
                }
                db.close();
            }
        });


        //video play
        youTubePlayerView.enableBackgroundPlayback(true);
        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                player = youTubePlayer;
                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                defaultPlayerUiController.showUi(false);

                //thanh chỉnh thời gian chạy của video
                YouTubePlayerSeekBar youTubePlayerSeekBar = findViewById(R.id.youtube_player_seekbar);
                if (darkMode) {
                    youTubePlayerSeekBar.setColor(getResources().getColor(R.color.white));
                    youTubePlayerSeekBar.getVideoCurrentTimeTextView()
                            .setTextColor(ContextCompat.getColor(VideoPlayActivity.this, android.R.color.white));
                    youTubePlayerSeekBar.getVideoDurationTextView()
                            .setTextColor(ContextCompat.getColor(VideoPlayActivity.this, android.R.color.white));
                } else {
                    youTubePlayerSeekBar.setColor(getResources().getColor(R.color.black));
                    youTubePlayerSeekBar.getVideoCurrentTimeTextView()
                            .setTextColor(ContextCompat.getColor(VideoPlayActivity.this, android.R.color.black));
                    youTubePlayerSeekBar.getVideoDurationTextView()
                            .setTextColor(ContextCompat.getColor(VideoPlayActivity.this, android.R.color.black));
                }
                youTubePlayerSeekBar.setYoutubePlayerSeekBarListener(new YouTubePlayerSeekBarListener() {
                    @Override
                    public void seekTo(float time) {
                        youTubePlayer.seekTo(time);
                    }
                });
                youTubePlayer.addListener(youTubePlayerSeekBar);

                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());

                youTubePlayer.loadVideo(itemId, 0);

                //them vao recently list
                db = openHelper.getWritableDatabase();
                db.delete("Recently", "videoId=?", new String[]{itemId});
                String sql = "insert into Recently(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                db.execSQL(sql, new String[]{itemId, tv_title.getText().toString(), thumbnails, tv_channel.getText().toString()});
                sql = "SELECT COUNT(*) FROM Recently";
                Cursor c = db.rawQuery(sql, null);
                int count = 0;
                if (c != null && c.moveToFirst()) {
                    count = c.getInt(0);
                    c.close();
                }
                if (count > 10) {
                    String tableName = "Recently";
                    String whereClause = "recId = (SELECT MIN(recId) FROM " + tableName + ")";
                    db.delete(tableName, whereClause, null);
                }
                db.close();

                //video control button
                checkControl = true;
                videoControl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkControl) {
                            youTubePlayer.pause();
                            if (darkMode) {
                                videoControl.setImageResource(R.drawable.baseline_play_arrow_24);

                            } else {
                                videoControl.setImageResource(R.drawable.baseline_play_arrow_24_dark);
                            }
                            checkControl = !checkControl;
                        } else {
                            youTubePlayer.play();
                            if (darkMode) {
                                videoControl.setImageResource(R.drawable.baseline_pause_24);
                            } else {
                                videoControl.setImageResource(R.drawable.baseline_pause_24_dark);
                            }
                            checkControl = !checkControl;
                        }
                    }
                });

                //skip control
                skipPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentId != null) {
                            controlSkip(currentId, v.getId(), youTubePlayer);
                        } else {
                            controlSkip(itemId, v.getId(), youTubePlayer);
                        }
                    }
                });

                skipNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentId != null) {
                            controlSkip(currentId, v.getId(), youTubePlayer);
                        } else {
                            controlSkip(itemId, v.getId(), youTubePlayer);
                        }
                    }
                });

                //suffle button
                checkSuffle = false;
                suffle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkSuffle) {
                            //todo: tat chuc nang suffle
                            if (darkMode) {
                                suffle.setImageResource(R.drawable.baseline_shuffle_24_light);
                            } else {
                                suffle.setImageResource(R.drawable.baseline_shuffle_24);

                            }
                            checkSuffle = !checkSuffle;
                        } else {
                            //todo: bat chuc nang suffle
                            //check chỉ một nút được bật tại 1 thời điểm (hoặc repeat hoặc suffle)
                            if (checkRepeat) {
                                if (darkMode) {
                                    repeat.setImageResource(R.drawable.baseline_repeat_24_light);
                                } else {
                                    repeat.setImageResource(R.drawable.baseline_repeat_24);
                                }
                                checkRepeat = !checkRepeat;
                            }
                            if (darkMode) {
                                suffle.setImageResource(R.drawable.baseline_shuffle_on_24_light);
                            } else {
                                suffle.setImageResource(R.drawable.baseline_shuffle_on_24);
                            }
                            checkSuffle = !checkSuffle;
                        }
                    }
                });

                //repeat button
                checkRepeat = false;
                repeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkRepeat) {
                            //todo: tat chuc nang repeat
                            if (darkMode) {
                                repeat.setImageResource(R.drawable.baseline_repeat_24_light);
                            } else {
                                repeat.setImageResource(R.drawable.baseline_repeat_24);
                            }
                            checkRepeat = !checkRepeat;
                        } else {
                            //todo: bat chuc nang repeat
                            if (checkSuffle) {
                                if (darkMode) {
                                    suffle.setImageResource(R.drawable.baseline_shuffle_24_light);
                                } else {
                                    suffle.setImageResource(R.drawable.baseline_shuffle_24);
                                }
                                checkSuffle = !checkSuffle;
                            }
                            if (darkMode) {
                                repeat.setImageResource(R.drawable.baseline_repeat_on_24_light);
                            } else {
                                repeat.setImageResource(R.drawable.baseline_repeat_on_24);
                            }
                            checkRepeat = !checkRepeat;
                        }
                    }
                });
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);
                Log.i("state", state + "");
                //xử lý chức năng repeat
                if (state.equals(PlayerConstants.PlayerState.ENDED) && checkRepeat) {
                    if (currentId != null) {
                        youTubePlayer.loadVideo(currentId, 0);
                    } else {
                        youTubePlayer.loadVideo(itemId, 0);
                    }
                } else if (state.equals(PlayerConstants.PlayerState.ENDED) && checkSuffle) {
                    if (currentId != null) {
                        controlSuffle(currentId, youTubePlayer);
                    } else {
                        controlSuffle(itemId, youTubePlayer);
                    }
                }
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).fullscreen(1).build();
        youTubePlayerView.initialize(listener, options);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPreferencesId = getSharedPreferences("IdSharePref", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesVideoDetail = getSharedPreferences("VideoDetailSharePref", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefPlayerBar = getSharedPreferences("PlayerBarSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefVideoDetailEdit = sharedPreferencesVideoDetail.edit();
        currentId = sharedPreferencesId.getString("currentId", null);
        prevId = sharedPreferencesId.getString("prevId", null);
        boolean isPlayerBarClicked = sharedPrefPlayerBar.getBoolean("isClicked", false);


        Log.i("id1", itemId + " itemId");
        Log.i("id1", currentId + " currentId");
        Log.i("id1", prevId + " prevId");

        //add liked track
        heart = findViewById(R.id.imv_heart_icon);
        emptyHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_empty); //empty heart clip
        fillHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_fill); //fill heart clip
        openHelper = new MySQLiteOpenHelper(this, "ProjectDB", null, 1);
        db = openHelper.getReadableDatabase();
        String sql = "select * from LikedTracks where videoId = ?";
        String[] selectionArgs = {currentId};
        Cursor c = db.rawQuery(sql, selectionArgs);
        boolean likedVideo = c.moveToFirst();
        Log.d("likedVideo", String.valueOf(likedVideo));
        c.close();
        if (likedVideo) {
            heart.setImageDrawable(fillHeart);
            fillHeart.start();
            full = true;
        } else {
            heart.setImageDrawable(emptyHeart);
            emptyHeart.start();
            full = false;
        }
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatedVectorDrawable drawable = full ? emptyHeart : fillHeart;
                if (drawable == emptyHeart) {
                    db = openHelper.getWritableDatabase();
                    db.delete("LikedTracks", "videoId=?", new String[]{currentId});
                    db.close();
                } else {
                    db = openHelper.getWritableDatabase();
                    String sql = "insert into LikedTracks(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                    db.execSQL(sql, new String[]{currentId, tv_title.getText().toString(), thumbnails, tv_channel.getText().toString()});
                    db.close();
                }
                heart.setImageDrawable(drawable);
                drawable.start();
                full = !full;
            }
        });


        VideoDataUtils.getVideoById(currentId).observe(this, new Observer<List<SingleItem>>() {
            @Override
            public void onChanged(List<SingleItem> singleItems) {
                tv_title.setText(singleItems.get(0).getSnippet().getTitle());
                tv_channel.setText(singleItems.get(0).getSnippet().getChannelTitle());
                thumbnails = singleItems.get(0).getSnippet().getThumbnails().getMedium().getUrl();

                if (!isPlayerBarClicked) {
                    if (!(currentId.equals(prevId))) {
                        player.loadVideo(currentId, 0);
                    }
                }

                sharedPrefVideoDetailEdit.putString("title", singleItems.get(0).getSnippet().getTitle());
                sharedPrefVideoDetailEdit.putString("channelTitle", singleItems.get(0).getSnippet().getChannelTitle());
                sharedPrefVideoDetailEdit.apply();

                //them vao recently list
                db = openHelper.getWritableDatabase();
                db.delete("Recently", "videoId=?", new String[]{currentId});
                String sql = "insert into Recently(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                db.execSQL(sql, new String[]{currentId, tv_title.getText().toString(), thumbnails, tv_channel.getText().toString()});
                sql = "SELECT COUNT(*) FROM Recently";
                Cursor c = db.rawQuery(sql, null);
                int count = 0;
                if (c != null && c.moveToFirst()) {
                    count = c.getInt(0);
                    c.close();
                }
                if (count > 10) {
                    String tableName = "Recently";
                    String whereClause = "recId = (SELECT MIN(recId) FROM " + tableName + ")";
                    db.delete(tableName, whereClause, null);
                }
                db.close();


            }
        });
        VideoDataUtils.getRelatedVideoData(currentId).observe(this, new Observer<List<SearchItem>>() {
            @Override
            public void onChanged(List<SearchItem> searchItems) {
                String recommendId;
                Random random = new Random();
                int randomVideoIndex = random.nextInt(searchItems.size()) / 10;
                recommendId = searchItems.get(randomVideoIndex).getId().getVideoId();
                title = searchItems.get(randomVideoIndex).getSnippet().getTitle();
                thumbnail = searchItems.get(randomVideoIndex).getSnippet().getThumbnails().getMedium().getUrl();
                channel = searchItems.get(randomVideoIndex).getSnippet().getChannelTitle();
                db = openHelper.getWritableDatabase();
                db.delete("Recommends", "videoId=?", new String[]{recommendId});
                String sql = "insert into Recommends(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                db.execSQL(sql, new String[]{recommendId, title, thumbnail, channel});
                sql = "SELECT COUNT(*) FROM Recommends";
                Cursor c = db.rawQuery(sql, null);
                int count = 0;
                if (c != null && c.moveToFirst()) {
                    count = c.getInt(0);
                    c.close();
                }
                if (count > 10) {
                    String tableName = "Recommends";
                    String whereClause = "recommendId = (SELECT MIN(recommendId) FROM " + tableName + ")";
                    db.delete(tableName, whereClause, null);
                }
                db.close();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferencesVideoDetail = getSharedPreferences("VideoDetailSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefVideoDetailEdit = sharedPreferencesVideoDetail.edit();
        sharedPrefVideoDetailEdit.clear();
        sharedPrefVideoDetailEdit.apply();
    }

    public void controlSkip(String videoId, int btnId, YouTubePlayer youTubePlayer) {
        SharedPreferences sharedPreferencesVideoDetail = getSharedPreferences("VideoDetailSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefVideoDetailEdit = sharedPreferencesVideoDetail.edit();
        SharedPreferences sharedPreferencesSkip = getSharedPreferences("SkipSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesSkipEdit = sharedPreferencesSkip.edit();
        SharedPreferences sharedPreferencesId = getSharedPreferences("IdSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesIdEdit = sharedPreferencesId.edit();


        VideoDataUtils.getRelatedVideoData(videoId).observe(this, new Observer<List<SearchItem>>() {
            @Override
            public void onChanged(List<SearchItem> searchItems) {
                String vId;
                sharedPreferencesSkipEdit.putBoolean("isSkip", true);
                sharedPreferencesSkipEdit.apply();
                if (btnId == skipNext.getId()) {
                    currentVideoIndex++;
                    if (currentVideoIndex >= searchItems.size()) {
                        currentVideoIndex = 0;
                    }
                    vId = searchItems.get(currentVideoIndex).getId().getVideoId();
                    tv_title.setText(searchItems.get(currentVideoIndex).getSnippet().getTitle());
                    tv_channel.setText(searchItems.get(currentVideoIndex).getSnippet().getChannelTitle());
                    itemId = searchItems.get(currentVideoIndex).getId().getVideoId();

                    String title = searchItems.get(currentVideoIndex).getSnippet().getTitle();
                    String channelTitle = searchItems.get(currentVideoIndex).getSnippet().getChannelTitle();
                    String thumbnail = searchItems.get(currentVideoIndex).getSnippet().getThumbnails().getMedium().getUrl();
                    addLikeTrack(vId, thumbnail, title, channelTitle);

                    sharedPreferencesIdEdit.putString("currentId", vId);
                    sharedPreferencesIdEdit.putString("prevId", vId);
                    sharedPrefVideoDetailEdit.putString("title", searchItems.get(currentVideoIndex).getSnippet().getTitle());
                    sharedPrefVideoDetailEdit.putString("channelTitle", searchItems.get(currentVideoIndex).getSnippet().getChannelTitle());
                    sharedPrefVideoDetailEdit.apply();
                    youTubePlayer.loadVideo(vId, 0);
                } else if (btnId == skipPrev.getId()) {
                    currentVideoIndex--;
                    if (currentVideoIndex < 0) {
                        currentVideoIndex = searchItems.size() - 1;
                    }
                    vId = searchItems.get(currentVideoIndex).getId().getVideoId();
                    tv_title.setText(searchItems.get(currentVideoIndex).getSnippet().getTitle());
                    tv_channel.setText(searchItems.get(currentVideoIndex).getSnippet().getChannelTitle());
                    itemId = searchItems.get(currentVideoIndex).getId().getVideoId();

                    String title = searchItems.get(currentVideoIndex).getSnippet().getTitle();
                    String channelTitle = searchItems.get(currentVideoIndex).getSnippet().getChannelTitle();
                    String thumbnail = searchItems.get(currentVideoIndex).getSnippet().getThumbnails().getMedium().getUrl();
                    addLikeTrack(vId, thumbnail, title, channelTitle);

                    sharedPreferencesIdEdit.putString("currentId", vId);
                    sharedPreferencesIdEdit.putString("prevId", vId);
                    sharedPrefVideoDetailEdit.putString("title", searchItems.get(currentVideoIndex).getSnippet().getTitle());
                    sharedPrefVideoDetailEdit.putString("channelTitle", searchItems.get(currentVideoIndex).getSnippet().getChannelTitle());
                    sharedPrefVideoDetailEdit.apply();
                    youTubePlayer.loadVideo(vId, 0);
                }
                sharedPreferencesIdEdit.apply();
            }
        });
    }

    public void controlSuffle(String videoId, YouTubePlayer youTubePlayer) {
        SharedPreferences sharedPreferencesVideoDetail = getSharedPreferences("VideoDetailSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefVideoDetailEdit = sharedPreferencesVideoDetail.edit();
        SharedPreferences sharedPreferencesId = getSharedPreferences("IdSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesIdEdit = sharedPreferencesId.edit();
        SharedPreferences sharedPrefSuffle = getSharedPreferences("SuffleSharePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefSuffleEdit = sharedPrefSuffle.edit();
        VideoDataUtils.getRelatedVideoData(videoId).observe(this, new Observer<List<SearchItem>>() {
            @Override
            public void onChanged(List<SearchItem> searchItems) {
                Random random = new Random();
                String vId;
                int randomVideoIndex = random.nextInt(searchItems.size()) / 10;
                vId = searchItems.get(randomVideoIndex).getId().getVideoId();
                sharedPrefSuffleEdit.putBoolean("isSuffle", true);
                sharedPrefSuffleEdit.apply();
                sharedPreferencesIdEdit.putString("currentId", vId);
                sharedPreferencesIdEdit.putString("prevId", vId);
                sharedPreferencesIdEdit.apply();
                tv_title.setText(searchItems.get(randomVideoIndex).getSnippet().getTitle());
                tv_channel.setText(searchItems.get(randomVideoIndex).getSnippet().getChannelTitle());
                itemId = searchItems.get(randomVideoIndex).getId().getVideoId();
                sharedPrefVideoDetailEdit.putString("title", searchItems.get(randomVideoIndex).getSnippet().getTitle());
                sharedPrefVideoDetailEdit.putString("channelTitle", searchItems.get(randomVideoIndex).getSnippet().getChannelTitle());
                sharedPrefVideoDetailEdit.apply();

                youTubePlayer.loadVideo(vId, 0);
            }
        });
    }

    public void onBack(View view) {
        videoPage = findViewById(R.id.layout_video_play);
        Intent intent = new Intent(VideoPlayActivity.this, MainActivity.class);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        videoPage.startAnimation(slideDown);
        moveTaskToBack(true);
        startActivity(intent);
    }

    public void addLikeTrack(String videoId, String thumbnail, String title, String channelTitle) {
        //add liked track
        heart = findViewById(R.id.imv_heart_icon);
        emptyHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_empty); //empty heart clip
        fillHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_fill); //fill heart clip
        openHelper = new MySQLiteOpenHelper(this, "ProjectDB", null, 1);
        db = openHelper.getReadableDatabase();
        String sql = "select * from LikedTracks where videoId = ?";
        String[] selectionArgs = {videoId};
        Cursor c = db.rawQuery(sql, selectionArgs);
        boolean likedVideo = c.moveToFirst();
        Log.d("likedVideo", String.valueOf(likedVideo));
        c.close();
        if (likedVideo) {
            heart.setImageDrawable(fillHeart);
            fillHeart.start();
            full = true;
        } else {
            heart.setImageDrawable(emptyHeart);
            emptyHeart.start();
            full = false;
        }
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatedVectorDrawable drawable = full ? emptyHeart : fillHeart;
                if (drawable == emptyHeart) {
                    db = openHelper.getWritableDatabase();
                    db.delete("LikedTracks", "videoId=?", new String[]{videoId});
                    db.close();
                } else {
                    db = openHelper.getWritableDatabase();
                    String sql = "insert into LikedTracks(videoId,title,thumbnails,channelTitle) values(?,?,?,?)";
                    db.execSQL(sql, new String[]{videoId, title, thumbnail, channelTitle});
                    db.close();
                }
                heart.setImageDrawable(drawable);
                drawable.start();
                full = !full;
            }
        });
    }
}