package com.pickni.wallpaper;

import android.app.Application;

import com.pickni.wallpaper.common.ObjectBox;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }
}