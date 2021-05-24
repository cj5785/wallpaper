package com.pickni.wallpaper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.pickni.lib_log.HiLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.library.tool.utility.device
 * create at 2018/12/24  11:54
 * description:
 */
public class RomUtil {

    /**
     * 判断是否为华为系统
     */
    public static boolean isHuaweiRom() {
        if (!TextUtils.isEmpty(getEmuiVersion()) && !getEmuiVersion().equals("")) {
            HiLog.d("isHuaweiRom: true");
            return true;
        }
        HiLog.d("isHuaweiRom: false");
        return false;
    }

    /**
     * 判断是否为小米系统
     */
    public static boolean isMiuiRom() {
        if (!TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"))) {
            HiLog.d("isMiuiRom: true");
            return true;
        }
        HiLog.d("isMiuiRom: false");
        return false;
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            HiLog.e("Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    HiLog.e("Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    /**
     * 判断是否为Flyme系统
     */
    public static boolean isFlymeRom(Context context) {
        HiLog.d("isFlymeRom: " + isInstalledByPkgName(context, "com.meizu.flyme.update"));
        return isInstalledByPkgName(context, "com.meizu.flyme.update");
    }

    /**
     * 判断是否是Smartisan系统
     */
    public static boolean isSmartisanRom(Context context) {
        HiLog.d("isSmartisanRom: " + isInstalledByPkgName(context, "com.smartisanos.security"));
        return isInstalledByPkgName(context, "com.smartisanos.security");
    }

    /**
     * 根据包名判断这个app是否已安装
     */
    public static boolean isInstalledByPkgName(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return 只要返回不是""，则是EMUI版本
     */
    private static String getEmuiVersion() {
        String emuiVerion = "";
        Class<?>[] clsArray = new Class<?>[]{String.class};
        Object[] objArray = new Object[]{"ro.build.version.emui"};
        try {
            @SuppressLint("PrivateApi") Class<?> SystemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method get = SystemPropertiesClass.getDeclaredMethod("get", clsArray);
            String version = (String) get.invoke(SystemPropertiesClass, objArray);
            HiLog.d("get EMUI version is:" + version);
            if (!TextUtils.isEmpty(version)) {
                return version;
            }
        } catch (ClassNotFoundException e) {
            HiLog.e(" getEmuiVersion wrong, ClassNotFoundException");
        } catch (LinkageError e) {
            HiLog.e(" getEmuiVersion wrong, LinkageError");
        } catch (NoSuchMethodException e) {
            HiLog.e(" getEmuiVersion wrong, NoSuchMethodException");
        } catch (NullPointerException e) {
            HiLog.e(" getEmuiVersion wrong, NullPointerException");
        } catch (Exception e) {
            HiLog.e(" getEmuiVersion wrong");
        }
        return emuiVerion;
    }
}