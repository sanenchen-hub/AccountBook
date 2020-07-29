package com.sanenchen.UsersManager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sanenchen.UsersManager.R;

/**
 * 关于界面
 * @author sanenchen
 * @version v1.0
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        /*配置toolbar*/
        Toolbar toolbar = findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("关于");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListenView();
    }

    private void ListenView() {
        // 在GitHub上的项目
        FrameLayout theAppInGithub = findViewById(R.id.theAppInGithub);
        theAppInGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/sanenchen-hub/ClassWarning");
                intent.setData(content_url);
                startActivity(intent);
            }
        });

        // 个人博客
        FrameLayout selfBlog = findViewById(R.id.selfBlog);
        selfBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://blog.lyqmc.cn");
                intent.setData(content_url);
                startActivity(intent);
            }
        });

        // 个人Github
        FrameLayout selfGithub = findViewById(R.id.selfGithub);
        selfGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/sanenchen-hub");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();//没其他按钮了，偷个懒
        return true;
    }
}