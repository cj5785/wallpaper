package com.pickni.wallpaper.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.pickni.lib_log.HiLog;
import com.pickni.wallpaper.BuildConfig;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Utils {

    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    public static boolean isBuildTypeDebug() {
        return BuildConfig.DEBUG;
    }

    private static String gProcessNameCache = null;

    public static String getCurrentProcessName(Context context) {
        if (gProcessNameCache != null) {
            return gProcessNameCache;
        }

        String processName = null;
        BufferedReader cmdlineReader = null;
        try {
            cmdlineReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "/proc/" + android.os.Process.myPid() + "/cmdline"),
                    "iso-8859-1"));
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = cmdlineReader.read()) > 0) {
                sb.append((char) c);
            }
            processName = sb.toString();

        } catch (IOException e) {
            HiLog.e("getCurrentProcessName: ", e);

        } finally {
            IOUtils.closeQuietly(cmdlineReader);
        }

        if (TextUtils.isEmpty(processName)) {
            HiLog.w("Fail to get process name by /proc/{pid}/cmdline, fallback to list all process mode!");
            int myPid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                List runningProcesses = activityManager.getRunningAppProcesses();
                if (runningProcesses != null) {
                    for (Object runningProcess : runningProcesses) {
                        ActivityManager.RunningAppProcessInfo process = (ActivityManager.RunningAppProcessInfo) runningProcess;
                        if (process.pid == myPid) {
                            processName = process.processName;
                            break;
                        }
                    }
                }
            }
        }
        gProcessNameCache = processName;
        return processName;
    }
}
