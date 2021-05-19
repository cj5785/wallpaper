package com.pickni.wallpaper;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public enum ResourceType {

    STATIC(R.string.wallpaper_static, R.drawable.ic_vector_static_img),
    DYNAMIC(R.string.wallpaper_dynamic, R.drawable.ic_vector_dynamic_img);

    @StringRes
    private final int title;
    @DrawableRes
    private final int drawable;

    ResourceType(@StringRes int title, @DrawableRes int drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public int getTitle() {
        return title;
    }

    public int getDrawable() {
        return drawable;
    }
}
