package com.pickni.wallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.pickni.wallpaper.common.Constants;
import com.pickni.wallpaper.common.ObjectBox;
import com.pickni.wallpaper.common.WallpaperImageData;
import com.pickni.wallpaper.common.WallpaperVideoData;
import com.pickni.wallpaper.event.DynamicWallpaperAddEvent;
import com.pickni.wallpaper.event.StaticWallpaperAddEvent;
import com.pickni.wallpaper.fragment.DefaultFragment;
import com.pickni.wallpaper.fragment.WallpaperDynamicFragment;
import com.pickni.wallpaper.fragment.WallpaperStaticFragment;
import com.pickni.wallpaper.service.ResetWallpaperService;
import com.pickni.wallpaper.service.VideoWallpaperService;
import com.pickni.wallpaper.utils.BitmapUtils;
import com.pickni.wallpaper.utils.WallpaperUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final int SET_WALLPAPER = 0x100;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private WallpaperManager mWallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWallpaperManager = WallpaperManager.getInstance(this);
        initView();
    }

    private void initView() {
        initToolBar();
        initDrawerLayout();
        initNavigation();
        initContent();
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    private void initNavigation() {
        navigationView = findViewById(R.id.navigation_view);
        ImageView avatarImage = navigationView.getHeaderView(0).findViewById(R.id.iv_avatar);
        TextView emailText = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
        Glide.with(avatarImage)
                .load(R.drawable.ic_avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(avatarImage);
        emailText.setText(Constants.EMAIL_ADDRESS);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_reset:
                        Intent intentWallpaper = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        intentWallpaper.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                new ComponentName(MainActivity.this, ResetWallpaperService.class));
                        startActivity(intentWallpaper);
                        break;
                    case R.id.action_settings:
                        Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intentSettings);
                        break;
                    case R.id.action_about:
                        Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intentAbout);
                        break;
                }
                return false;
            }
        });
    }

    private void initContent() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        TypePagerAdapter adapter = new TypePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = View.inflate(this, R.layout.view_tab_item, null);
            TextView tv_name = view.findViewById(R.id.text_view);
            ImageView iv_icon = view.findViewById(R.id.image_view);
            tv_name.setText(ResourceType.values()[i].getTitle());
            iv_icon.setImageResource(ResourceType.values()[i].getDrawable());
            if (tab != null) {
                tab.setCustomView(view);
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabSelected: " + tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabUnselected: " + tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabReselected: " + tab);
            }
        });
    }

    @Override
    protected void onDestroy() {
        drawerLayout.removeDrawerListener(toggle);
        super.onDestroy();
    }

    class TypePagerAdapter extends FragmentPagerAdapter {

        public TypePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(ResourceType.values()[position].getTitle());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            ResourceType type = ResourceType.values()[position];
            switch (type) {
                case STATIC:
                    WallpaperStaticFragment staticFragment = new WallpaperStaticFragment();
                    staticFragment.setOnFragmentListener(mOnStaticFragmentListener);
                    return staticFragment;
                case DYNAMIC:
                    WallpaperDynamicFragment dynamicFragment = new WallpaperDynamicFragment();
                    dynamicFragment.setOnFragmentListener(mOnDynamicFragmentListener);
                    return dynamicFragment;
                default:
                    return new DefaultFragment();
            }
        }

        @Override
        public int getCount() {
            return ResourceType.values().length;
        }
    }

    private final WallpaperStaticFragment.OnFragmentListener mOnStaticFragmentListener
            = new WallpaperStaticFragment.OnFragmentListener() {
        @Override
        public void onColorSelect(Drawable drawable) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            Bitmap bitmap = BitmapUtils.drawableToBitmap(drawable, width, height);
            try {
                mWallpaperManager.setBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onHeaderSelect() {
            PictureSelector.create(MainActivity.this)
                    .openGallery(PictureMimeType.ofImage())
                    .theme(R.style.picture_WeChat_style)
                    .selectionMode(PictureConfig.SINGLE)
                    .freeStyleCropEnabled(true)
                    .isEnableCrop(true)
                    .imageEngine(GlideEngine.createGlideEngine())
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            if (result == null || result.size() == 0) {
                                return;
                            }
                            LocalMedia localMedia = result.get(0);
                            if (localMedia == null) {
                                return;
                            }
                            File file = new File(localMedia.getCutPath());
                            if (!file.exists()) {
                                return;
                            }
                            WallpaperImageData data = new WallpaperImageData();
                            data.path = file.getAbsolutePath();
                            ObjectBox.get().boxFor(WallpaperImageData.class).put(data);
                            WallpaperUtils.setWallpaper(MainActivity.this, localMedia.getCutPath());
                            EventBus.getDefault().post(new StaticWallpaperAddEvent());
                        }

                        @Override
                        public void onCancel() {
                            // 取消
                        }
                    });

        }

        @Override
        public void onImagePathSelect(String imagePath) {
            WallpaperUtils.setWallpaper(MainActivity.this, imagePath);
        }
    };

    private final WallpaperDynamicFragment.OnFragmentListener mOnDynamicFragmentListener
            = new WallpaperDynamicFragment.OnFragmentListener() {
        @Override
        public void onHeaderSelect() {
            PictureSelector.create(MainActivity.this)
                    .openGallery(PictureMimeType.ofVideo())
                    .theme(R.style.picture_WeChat_style)
                    .selectionMode(PictureConfig.SINGLE)
                    .imageEngine(GlideEngine.createGlideEngine())
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            if (result == null || result.size() == 0) {
                                return;
                            }
                            LocalMedia localMedia = result.get(0);
                            if (localMedia == null) {
                                return;
                            }
                            File file = new File(localMedia.getRealPath());
                            if (!file.exists()) {
                                return;
                            }
                            WallpaperVideoData data = new WallpaperVideoData();
                            data.path = file.getAbsolutePath();
                            ObjectBox.get().boxFor(WallpaperVideoData.class).put(data);
                            VideoWallpaperService.setWallPaper(MainActivity.this, file.getAbsolutePath());
                            EventBus.getDefault().post(new DynamicWallpaperAddEvent());
                        }

                        @Override
                        public void onCancel() {
                            // 取消
                        }
                    });

        }

        @Override
        public void onVideoPathSelect(String videoPath) {
            Log.e(TAG, "onVideoPathSelect: " + videoPath);
            VideoWallpaperService.setWallPaper(MainActivity.this, videoPath);
        }
    };
}