package com.pickni.wallpaper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.pickni.wallpaper.fragment.DefaultFragment;
import com.pickni.wallpaper.fragment.WallpaperDynamicFragment;
import com.pickni.wallpaper.fragment.WallpaperStaticFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
//        Intent intent = new Intent(this, MyLwp.class);
//        startService(intent);

//        Intent intent = new Intent(
//                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
//        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
//                new ComponentName(this, MyLwp.class));
//        startActivity(intent);
    }

    private void initView() {
        toolbar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }

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

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        TypePagerAdapter adapter = new TypePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = View.inflate(this, R.layout.tab_item, null);
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
                    return new WallpaperStaticFragment();
                case DYNAMIC:
                    return new WallpaperDynamicFragment();
                default:
                    return new DefaultFragment();
            }
        }

        @Override
        public int getCount() {
            return ResourceType.values().length;
        }
    }
}