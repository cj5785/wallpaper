package com.pickni.wallpaper.utils;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.pickni.wallpaper.common.Constants;

import java.io.File;
import java.io.IOException;

/**
 * date        : 2021/5/20 15:27
 * author      : JerRay
 * email       : chenjietop@gmail.com
 * description :
 */
public class WallpaperUtils {
    public static void setWallpaper(Context context, String path) {
        if (context == null || TextUtils.isEmpty(path)) {
            return;
        }
        Uri uriPath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriPath =  FileProvider.getUriForFile(context.getApplicationContext(), Constants.APP_PROVIDER, new File(path));
        } else {
            uriPath =  Uri.fromFile(new File(path));
        }
        Intent intent;
        if (RomUtil.isHuaweiRom()) {
            try {
                ComponentName componentName =
                        new ComponentName("com.android.gallery3d", "com.android.gallery3d.app.Wallpaper");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uriPath, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    WallpaperManager.getInstance(context.getApplicationContext())
                            .setBitmap(BitmapFactory.decodeFile(path));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (RomUtil.isMiuiRom()) {
            try {
                ComponentName componentName = new ComponentName("com.android.thememanager",
                        "com.android.thememanager.activity.WallpaperDetailActivity");
                intent = new Intent("miui.intent.action.START_WALLPAPER_DETAIL");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uriPath, "image/*");
                intent.putExtra("mimeType", "image/*");
                intent.setComponent(componentName);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    WallpaperManager.getInstance(context.getApplicationContext())
                            .setBitmap(BitmapFactory.decodeFile(path));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    intent =
                            WallpaperManager.getInstance(context.getApplicationContext()).getCropAndSetWallpaperIntent(uriPath);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);
                } catch (IllegalArgumentException e) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), uriPath);
                        if (bitmap != null) {
                            WallpaperManager.getInstance(context.getApplicationContext()).setBitmap(bitmap);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                try {
                    WallpaperManager.getInstance(context.getApplicationContext())
                            .setBitmap(BitmapFactory.decodeFile(path));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
