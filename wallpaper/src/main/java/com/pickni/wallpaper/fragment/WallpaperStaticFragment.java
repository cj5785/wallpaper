package com.pickni.wallpaper.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pickni.wallpaper.R;
import com.pickni.wallpaper.adapter.ColorAdapter;
import com.pickni.wallpaper.adapter.CustomizeAdapter;
import com.pickni.wallpaper.common.ObjectBox;
import com.pickni.wallpaper.common.WallpaperData;
import com.pickni.wallpaper.event.StaticWallpaperAddEvent;
import com.pickni.wallpaper.utils.ColorUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class WallpaperStaticFragment extends Fragment {

    private CustomizeAdapter mCustomizeAdapter;

    public interface OnFragmentListener {
        void onColorSelect(Drawable drawable);

        void onHeaderSelect();

        void onImagePathSelect(String imagePath);
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
        View view = inflater.inflate(R.layout.view_fragment_static, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView(View view) {
        RecyclerView solidRecyclerView = view.findViewById(R.id.recycler_view_color_solid);
        solidRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        ColorAdapter solidAdapter = new ColorAdapter();
        solidAdapter.setOnColorItemClickListener(mOnColorItemClickListener);
        solidRecyclerView.setAdapter(solidAdapter);
        solidAdapter.setData(ColorUtils.getColorDrawableList());

        RecyclerView gradientRecyclerView = view.findViewById(R.id.recycler_view_color_gradient);
        gradientRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        ColorAdapter gradientAdapter = new ColorAdapter();
        gradientAdapter.setOnColorItemClickListener(mOnColorItemClickListener);
        gradientRecyclerView.setAdapter(gradientAdapter);
        gradientAdapter.setData(ColorUtils.getGradientDrawableList());

        RecyclerView customizeRecyclerView = view.findViewById(R.id.recycler_view_color_customize);
        customizeRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mCustomizeAdapter = new CustomizeAdapter();
        mCustomizeAdapter.setOnCustomItemClickListener(mOnCustomItemClickListener);
        customizeRecyclerView.setAdapter(mCustomizeAdapter);
        queryAllLocalData();
    }

    private final ColorAdapter.OnColorItemClickListener mOnColorItemClickListener
            = drawable -> {
        if (mOnFragmentListener != null) {
            mOnFragmentListener.onColorSelect(drawable);
        }
    };

    private final CustomizeAdapter.OnCustomItemClickListener mOnCustomItemClickListener
            = new CustomizeAdapter.OnCustomItemClickListener() {
        @Override
        public void onHeaderClicked() {
            if (mOnFragmentListener != null) {
                mOnFragmentListener.onHeaderSelect();
            }
        }

        @Override
        public void onItemClicked(String imagePath) {
            if (mOnFragmentListener != null) {
                mOnFragmentListener.onImagePathSelect(imagePath);
            }
        }

        @Override
        public void onFooterClicked() {

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStaticWallpaperAddEvent(StaticWallpaperAddEvent event) {
        queryAllLocalData();
    }

    private void queryAllLocalData() {
        List<WallpaperData> allData = ObjectBox.get().boxFor(WallpaperData.class).getAll();
        List<String> imagePathList = new ArrayList<>();
        for (WallpaperData data : allData) {
            imagePathList.add(data.path);
        }
        mCustomizeAdapter.setData(imagePathList);
    }
}
