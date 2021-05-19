package com.pickni.wallpaper.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pickni.wallpaper.R;

public class WallpaperDynamicFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setBackgroundColor(Color.rgb(
                (int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)));
        textView.setText(R.string.wallpaper_dynamic);
        return textView;
    }
}
