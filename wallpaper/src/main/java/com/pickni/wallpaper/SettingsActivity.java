package com.pickni.wallpaper;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.pickni.wallpaper.common.Config;
import com.pickni.wallpaper.service.VideoWallpaperService;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        SwitchMaterial switchMaterial = findViewById(R.id.switch_video_sound);
        switchMaterial.setChecked(Config.getVideoSoundEnable(this));
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Config.setVideoSoundEnable(SettingsActivity.this, isChecked);
                VideoWallpaperService.setVolume(SettingsActivity.this, isChecked);
            }
        });
        AppCompatSeekBar seekBar = findViewById(R.id.seek_bar_video_volume);
        seekBar.setProgress((int) (Config.getVideoVolumeValue(SettingsActivity.this) * seekBar.getMax()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Config.setVideoVolumeValue(SettingsActivity.this, seekBar.getProgress() * 1F / seekBar.getMax());
            }
        });
    }
}