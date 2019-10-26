package com.devmohamedibrahim1997.populartest.UI;

import android.os.Bundle;
import android.widget.Toast;

import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.Utils.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends YouTubeBaseActivity {

    String videoKey;
    ArrayList<String> videosKeysArrayList;

    @BindView(R.id.youtubePlayerView)
    YouTubePlayerView youtubePlayerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            videoKey = getIntent().getExtras().getString("videoKey");
            videosKeysArrayList = getIntent().getExtras().getStringArrayList("videosKeysArrayList");

        }

        youtubePlayerView.initialize(YoutubeConfig.getYoutubeApiKey(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (videoKey != null) {
                    youTubePlayer.loadVideos(videosKeysArrayList);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(VideoPlayerActivity.this, "failed to load video", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
