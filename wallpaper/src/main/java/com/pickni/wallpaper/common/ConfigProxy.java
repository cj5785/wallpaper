package com.pickni.wallpaper.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.pickni.wallpaper.utils.Utils;

import java.io.File;

public class ConfigProxy {

    private String mConfigFileName;
    private String mTargetProcessName = null;
    private String mCurrentProcessNameCache = null;

    public ConfigProxy(String configFileName) {
        mConfigFileName = configFileName;
    }

    public ConfigProxy(String configFileName, String targetProcessName) {
        mConfigFileName = configFileName;
        mTargetProcessName = targetProcessName;
    }

    private void checkTargetProcess(Context context) {
        if (mTargetProcessName != null) {
            if (mCurrentProcessNameCache == null) {
                mCurrentProcessNameCache = Utils.getCurrentProcessName(context);
            }

            if (!mTargetProcessName.equals(mCurrentProcessNameCache)) {
                Log.e("ConfigProxy", "Not target process! CurrentProcess: " + mCurrentProcessNameCache + ", Target: " + mTargetProcessName +
                        Log.getStackTraceString(new Exception()));
            }
        }
    }

    @SuppressLint("SdCardPath")
    public File getConfigFile(Context context) {
        return new File("/data/data/" + context.getPackageName() + "/shared_prefs/" + mConfigFileName + ".xml");
    }

    public String getValue(Context context, String key, String defaultValue) {
        checkTargetProcess(context);

        SharedPreferences preferences = context.getSharedPreferences(mConfigFileName,
                Context.MODE_PRIVATE);
        if (preferences == null) {
            return defaultValue;
        }

        return preferences.getString(key, defaultValue);
    }

    public boolean setValue(Context context, String key, String value) {
        checkTargetProcess(context);

        SharedPreferences.Editor editor = createPreferenceEditor(context);
        if (editor == null) {
            return false;
        }

        editor.putString(key, value);
        return editor.commit();
    }

    // boolean
    public boolean getValue(Context context, String key, boolean defaultValue) {
        checkTargetProcess(context);

        SharedPreferences preferences = context.getSharedPreferences(mConfigFileName,
                Context.MODE_PRIVATE);
        if (preferences == null) {
            return defaultValue;
        }

        return preferences.getBoolean(key, defaultValue);
    }

    public boolean setValue(Context context, String key, boolean value) {
        checkTargetProcess(context);

        SharedPreferences.Editor editor = createPreferenceEditor(context);
        if (editor == null) {
            return false;
        }

        editor.putBoolean(key, value);
        return editor.commit();
    }

    // long
    public long getValue(Context context, String key, long defaultValue) {
        checkTargetProcess(context);

        SharedPreferences preferences = context.getSharedPreferences(mConfigFileName,
                Context.MODE_PRIVATE);
        if (preferences == null) {
            return defaultValue;
        }

        return preferences.getLong(key, defaultValue);
    }

    public boolean setValue(Context context, String key, long value) {
        checkTargetProcess(context);

        SharedPreferences.Editor editor = createPreferenceEditor(context);
        if (editor == null) {
            return false;
        }

        editor.putLong(key, value);
        return editor.commit();
    }

    // int
    public int getValue(Context context, String key, int defaultValue) {
        checkTargetProcess(context);

        SharedPreferences preferences = context.getSharedPreferences(mConfigFileName,
                Context.MODE_PRIVATE);
        if (preferences == null) {
            return defaultValue;
        }

        return preferences.getInt(key, defaultValue);
    }

    // float
    public float getValue(Context context, String key, float defaultValue) {
        checkTargetProcess(context);

        SharedPreferences preferences = context.getSharedPreferences(mConfigFileName,
                Context.MODE_PRIVATE);
        if (preferences == null) {
            return defaultValue;
        }

        return preferences.getFloat(key, defaultValue);
    }

    public boolean setValue(Context context, String key, float value) {
        checkTargetProcess(context);

        SharedPreferences.Editor editor = createPreferenceEditor(context);
        if (editor == null) {
            return false;
        }

        editor.putFloat(key, value);
        return editor.commit();
    }

    public boolean setValue(Context context, String key, int value) {
        checkTargetProcess(context);

        SharedPreferences.Editor editor = createPreferenceEditor(context);
        if (editor == null) {
            return false;
        }

        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean remove(Context context, String key) {
        checkTargetProcess(context);

        SharedPreferences.Editor editor = createPreferenceEditor(context);
        if (editor == null) {
            return false;
        }

        editor.remove(key);
        return editor.commit();
    }

    private SharedPreferences.Editor createPreferenceEditor(Context context) {
        checkTargetProcess(context);

        SharedPreferences preferences = context.getSharedPreferences(mConfigFileName,
                Context.MODE_PRIVATE);
        if (preferences == null) {
            return null;
        }

        return preferences.edit();
    }

    public void clearData(Context context) {
        checkTargetProcess(context);

        SharedPreferences.Editor editor = createPreferenceEditor(context);
        if (editor == null) {
            return;
        }

        editor.clear();
        editor.commit();
    }

    public boolean doesKeyExist(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(mConfigFileName,
                Context.MODE_PRIVATE);

        if (preferences == null) {
            return false;
        }

        return preferences.contains(key);
    }
}
