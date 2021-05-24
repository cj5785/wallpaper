package com.pickni.wallpaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pickni.wallpaper.common.Config;
import com.pickni.wallpaper.service.VideoWallpaperService;

import java.io.File;

public class VideoPreviewActivity extends BaseActivity {

    private static final String KEY_FILE_PATH = "file_path";
    private String mFilePath;

    public static void start(Activity activity, String filePath) {
        Intent intent = new Intent(activity, VideoPreviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_FILE_PATH, filePath);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);

        mFilePath = getIntent().getStringExtra(KEY_FILE_PATH);
        initView();
    }

    private void initView() {
        VideoView videoView = findViewById(R.id.video_view);
        MediaController controller = new MediaController(this);
        File videoFile = new File(mFilePath);
        if (videoFile.exists()) {
            videoView.setVideoPath(videoFile.getAbsolutePath());
            videoView.setMediaController(controller);
            videoView.setOnPreparedListener(mp -> {
                mp.start();
                mp.setLooping(true);
                float volume = Config.getVideoVolumeValue(getApplicationContext());
                if (Config.getVideoSoundEnable(getApplicationContext())) {
                    if (volume - 0 > 0.1F) {
                        mp.setVolume(volume, volume);
                        return;
                    }
                }
                mp.setVolume(0, 0);
            });
        }
        FloatingActionButton button = findViewById(R.id.fab_setting);
        button.setOnClickListener(v -> {
            VideoWallpaperService.setWallPaper(VideoPreviewActivity.this, mFilePath);
            finish();
        });
    }
}