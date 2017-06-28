package com.devfun.cartoon.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.devfun.cartoon.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mPlayerView = (YouTubePlayerView) findViewById(R.id.activityPlayVideo_youTubePlayerView_player);
        mPlayerView.initialize("AIzaSyDY88cPyqTT1hogpMtXO0N7HApLtRm_OGY", this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(getIntent().getStringExtra("videoId"));
        }else {
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra trong quá trình phát Video.", Toast.LENGTH_SHORT).show();
    }
}
