package com.pickni.wallpaper.service;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import com.pickni.wallpaper.common.Config;

import java.io.IOException;

/**
 * date        : 2021/5/20 16:34
 * author      : JerRay
 * email       : chenjietop@gmail.com
 * description :
 */
public class VideoWallpaperService extends WallpaperService {

    private static final String TAG = "VideoWallpaperService";
    public static final String VIDEO_PARAMS_CONTROL_ACTION = "com.pickni.wallpaper.service.VideoLiveWallpaper";
    public static final String KEY_ACTION = "action";
    public static final int ACTION_UPDATE_VIDEO_FILE_PATH = 0x100;
    public static final int ACTION_VOICE_SILENCE = 0x110;
    public static final int ACTION_VOICE_NORMAL = 0x111;
    private BroadcastReceiver mVideoParamsControlReceiver;
    private MediaPlayer mMediaPlayer;

    public Engine onCreateEngine() {
        return new VideoEngine();
    }

    class VideoEngine extends Engine {
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            Log.e(TAG, "onCreate: ");
            //Engine对象被创建时回调，这里可以做广播注册等操作
            super.onCreate(surfaceHolder);
            setVolume(getApplicationContext(), Config.getVideoSoundEnable(getApplicationContext()));
            IntentFilter intentFilter = new IntentFilter(VIDEO_PARAMS_CONTROL_ACTION);
            registerReceiver(mVideoParamsControlReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e(TAG, "onReceive: ");
                    if (mMediaPlayer == null) {
                        return;
                    }
                    int action = intent.getIntExtra(KEY_ACTION, -1);
                    switch (action) {
                        case ACTION_VOICE_NORMAL:
                            float volume = Config.getVideoVolumeValue(getApplicationContext());
                            mMediaPlayer.setVolume(volume, volume);
                            break;
                        case ACTION_VOICE_SILENCE:
                            mMediaPlayer.setVolume(0, 0);
                            break;
                        case ACTION_UPDATE_VIDEO_FILE_PATH:
                            //更新视频播放源
                            String videoFilePath = Config.getCurrentVideoPath(context);
                            try {
                                Uri parse = Uri.parse(videoFilePath);
                                Log.e(TAG, "onReceive: " + parse);
                                mMediaPlayer.setDataSource(videoFilePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }, intentFilter);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            //显示、隐藏时切换，在桌面时为显示，跳转到别的App页面时为隐藏，这里做视频的暂停和恢复播放
            if(mMediaPlayer!=null) {
                if (visible) {
                    mMediaPlayer.start();
                } else {
                    mMediaPlayer.pause();
                }
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            //SurfaceView被创建时回调，我们的视频MediaPlayer对象播放的视频输出在这个surface上
            super.onSurfaceCreated(holder);
            Log.e(TAG, "onSurfaceCreated: ");
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setSurface(holder.getSurface());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mMediaPlayer.setDataSource(Config.getCurrentVideoPath(getApplicationContext()));
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        float volume = Config.getVideoVolumeValue(getApplicationContext());
                        if (Config.getVideoSoundEnable(getApplicationContext())) {
                            if (volume - 0 > 0.1F) {
                                mMediaPlayer.setVolume(volume, volume);
                                return;
                            }
                        }
                        mMediaPlayer.setVolume(0, 0);
                    }
                });
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.e(TAG, "onSurfaceChanged: ");
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            //Surface销毁时回调，这里我们应该销毁MediaPlayer，回收MediaPlayer
            Log.e(TAG, "onSurfaceDestroyed: ");
            super.onSurfaceDestroyed(holder);
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        @Override
        public void onDestroy() {
            Log.e(TAG, "onDestroy: ");
            //Engine对象被销毁时回调，这里可以注销广播注册等操作
            unregisterReceiver(mVideoParamsControlReceiver);
            super.onDestroy();
        }
    }

    /**
     * 跳转到动态壁纸设置页面
     */
    public static void setWallPaper(Context context, String videoFilePath) {
        //因为没有提供是否设置成功的回调，只能当选择了就是设置成功
        saveVideoPath(context, videoFilePath);
        startNewWallpaper(context);
        updateWallpaperVideo(context);
    }

    private static void saveVideoPath(Context context, String videoFilePath) {
        //将视频地址保存，后面广播更新时再读取
        Config.setCurrentVideoPath(context, videoFilePath);
    }

    /**
     * 跳转到系统的动态壁纸设置界面
     */
    private static void startNewWallpaper(Context context) {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, VideoWallpaperService.class));
        context.startActivity(intent);
    }

    private static void updateWallpaperVideo(Context context) {
        //发送广播更新
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, ACTION_UPDATE_VIDEO_FILE_PATH);
        context.sendBroadcast(intent);
    }

    /**
     * 设置是否静音
     *
     * @param isSilence 是否静音
     */
    public static void setVolume(Context context, boolean isSilence) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        if (isSilence) {
            intent.putExtra(KEY_ACTION, ACTION_VOICE_SILENCE);
        } else {
            intent.putExtra(KEY_ACTION, ACTION_VOICE_NORMAL);
        }
        context.sendBroadcast(intent);
    }
}