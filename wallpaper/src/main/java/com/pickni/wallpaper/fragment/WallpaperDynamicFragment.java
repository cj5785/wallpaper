package com.pickni.wallpaper.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pickni.wallpaper.R;
import com.pickni.wallpaper.adapter.CustomizeVideoAdapter;
import com.pickni.wallpaper.common.ObjectBox;
import com.pickni.wallpaper.common.WallpaperVideoData;
import com.pickni.wallpaper.event.DynamicWallpaperAddEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class WallpaperDynamicFragment extends Fragment {

    private static final String TAG = "WallpaperDynamicFragmen";

    private CustomizeVideoAdapter mCustomizeVideoAdapter;

    public interface OnFragmentListener {

        void onHeaderSelect();

        void onVideoPathSelect(String videoPath);
    }

    private OnFragmentListener mOnFragmentListener;

    public void setOnFragmentListener(OnFragmentListener listener) {
        mOnFragmentListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment_dynamic, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView(View view) {
        RecyclerView customizeRecyclerView = view.findViewById(R.id.recycler_view_video_customize);
        customizeRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 5));
        mCustomizeVideoAdapter = new CustomizeVideoAdapter();
        mCustomizeVideoAdapter.setOnCustomItemClickListener(mOnCustomItemClickListener);
        customizeRecyclerView.setAdapter(mCustomizeVideoAdapter);
        queryAllLocalData();
    }

    private final CustomizeVideoAdapter.OnCustomItemClickListener mOnCustomItemClickListener
            = new CustomizeVideoAdapter.OnCustomItemClickListener() {
        @Override
        public void onHeaderClicked() {
            if (mOnFragmentListener != null) {
                mOnFragmentListener.onHeaderSelect();
            }
        }

        @Override
        public void onItemClicked(String videoPath) {
            if (mOnFragmentListener != null) {
                mOnFragmentListener.onVideoPathSelect(videoPath);
            }
        }

        @Override
        public void onFooterClicked() {

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDynamicWallpaperAddEvent(DynamicWallpaperAddEvent event) {
        queryAllLocalData();
    }

    private void queryAllLocalData() {
        List<WallpaperVideoData> allData = ObjectBox.get().boxFor(WallpaperVideoData.class).getAll();
        List<String> videoPathList = new ArrayList<>();
        for (WallpaperVideoData data : allData) {
            videoPathList.add(data.path);
        }
        mCustomizeVideoAdapter.setData(videoPathList);
    }
}
