package com.pickni.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.pickni.wallpaper.utils.Utils;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView versionTextView = findViewById(R.id.tv_version);

        String versionName = Utils.getVersionName();
        int versionCode = Utils.getVersionCode();

        versionTextView.setText(String.format("%s %s%s", versionTextView.getText(), versionName,
                (Utils.isBuildTypeDebug() ? "-" + versionCode : "")));

        ImageView ivIcon = findViewById(R.id.iv_icon);
        ivIcon.setClickable(true);
        TextView tvCopyRight = findViewById(R.id.tv_copy_right);
        tvCopyRight.setClickable(true);
    }
}