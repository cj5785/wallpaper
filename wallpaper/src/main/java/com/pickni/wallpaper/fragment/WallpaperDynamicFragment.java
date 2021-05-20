package com.pickni.wallpaper.fragment;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pickni.wallpaper.R;
import com.pickni.wallpaper.service.VideoWallpaperService;

public class WallpaperDynamicFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Button button = new Button(getContext());
        button.setOnClickListener(v -> {
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(activity, VideoWallpaperService.class));
                activity.startActivity(intent);
            }
        });
        button.setText(R.string.wallpaper_dynamic);
        return button;
    }
}
