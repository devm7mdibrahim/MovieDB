package com.devmohamedibrahim1997.populartest.ui.video;

import android.os.Bundle;
import android.widget.Toast;

import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.utils.YoutubeConfig;
import com.devmohamedibrahim1997.populartest.databinding.ActivityVideoPlayerBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import static com.devmohamedibrahim1997.populartest.utils.Constant.VIDEOS_KEYS;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.showToast;

public class VideoPlayerActivity extends YouTubeBaseActivity {

    ArrayList<String> videosKeysArrayList = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVideoPlayerBinding videoPlayerBinding = DataBindingUtil.setContentView(this,R.layout.activity_video_player);

        if (getIntent().getExtras() != null) {
            videosKeysArrayList = getIntent().getExtras().getStringArrayList(VIDEOS_KEYS);
        }

        videoPlayerBinding.youtubePlayerView.initialize(YoutubeConfig.getYoutubeApiKey(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (videosKeysArrayList != null) {
                    youTubePlayer.loadVideos(videosKeysArrayList);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                showToast(VideoPlayerActivity.this, "failed to load video");
            }
        });
    }
}
