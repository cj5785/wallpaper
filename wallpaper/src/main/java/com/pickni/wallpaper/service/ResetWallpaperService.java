package com.pickni.wallpaper.service;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * date        : 2021/5/20 17:34
 * author      : JerRay
 * email       : chenjietop@gmail.com
 * description :
 */
public class ResetWallpaperService extends WallpaperService {

    public WallpaperService.Engine onCreateEngine() {
        return new VideoEngine();
    }

    class VideoEngine extends Engine {

        @Override
        public void onVisibilityChanged(boolean visible) {
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) wallpaperDrawable).getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    Paint paint = new Paint();
                    canvas.drawBitmap(bitmap, 0, 0, paint);
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}