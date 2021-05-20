package com.pickni.wallpaper.common;

import android.content.Context;

import java.io.File;

public class Config {
    private static final String CONFIG_FILE_NAME = "main";

    private static final ConfigProxy gConfigProxy = new ConfigProxy(CONFIG_FILE_NAME);

    private static final String KEY_VIDEO_SOUND_ENABLE = "video_sound_enable";
    private static final String KEY_VIDEO_VOLUME_VALUE = "video_volume_value";
    private static final String KEY_CURRENT_VIDEO_PATH = "current_video_path";

    public static File getConfigFile(Context context) {
        return gConfigProxy.getConfigFile(context);
    }

    public static boolean getVideoSoundEnable(Context context) {
        return gConfigProxy.getValue(context, KEY_VIDEO_SOUND_ENABLE, false);
    }

    public static boolean setVideoSoundEnable(Context context, boolean value) {
        return gConfigProxy.setValue(context, KEY_VIDEO_SOUND_ENABLE, value);
    }

    public static float getVideoVolumeValue(Context context) {
        return gConfigProxy.getValue(context, KEY_VIDEO_VOLUME_VALUE, 0F);
    }

    public static boolean setVideoVolumeValue(Context context, float value) {
        return gConfigProxy.setValue(context, KEY_VIDEO_VOLUME_VALUE, value);
    }

    public static String getCurrentVideoPath(Context context) {
        return gConfigProxy.getValue(context, KEY_CURRENT_VIDEO_PATH, null);
    }

    public static boolean setCurrentVideoPath(Context context, String value) {
        return gConfigProxy.setValue(context, KEY_CURRENT_VIDEO_PATH, value);
    }
}
