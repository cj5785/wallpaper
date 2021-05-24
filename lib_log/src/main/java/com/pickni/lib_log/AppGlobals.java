package com.pickni.lib_log;

import android.annotation.SuppressLint;
import android.app.Application;

public class AppGlobals {
    private static AppGlobals gInstance;
    private static Application gApplication;

    private AppGlobals() {
    }

    public static AppGlobals getInstance() {
        if (gInstance == null) {
            synchronized (AppGlobals.class) {
                if (gInstance == null) {
                    gInstance = new AppGlobals();
                }
            }
        }
        return gInstance;
    }

    @SuppressLint("PrivateApi")
    public Application getApplication() {
        if (gApplication == null) {
            try {
                gApplication = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gApplication;
    }
}
