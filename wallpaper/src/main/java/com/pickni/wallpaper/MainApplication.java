package com.pickni.wallpaper;

import android.app.Application;

import com.google.gson.Gson;
import com.pickni.lib_log.HiConsolePrinter;
import com.pickni.lib_log.HiFilePrinter;
import com.pickni.lib_log.HiLogConfig;
import com.pickni.lib_log.HiLogManager;
import com.pickni.wallpaper.common.ObjectBox;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
        HiLogManager.init(new HiLogConfig() {
            @Override
            public JsonParser injectJsonParser() {
                return src -> new Gson().toJson(src);
            }

            @Override
            public String getGlobalTag() {
                return "MainApplication";
            }

            @Override
            public boolean enable() {
                return BuildConfig.DEBUG;
            }

            @Override
            public boolean includeThread() {
                return true;
            }

            @Override
            public int stackTraceDepth() {
                return 5;
            }
        }, new HiConsolePrinter());
        HiFilePrinter.getInstance(getCacheDir().getAbsoluteFile().getAbsolutePath(), 0);
    }
}