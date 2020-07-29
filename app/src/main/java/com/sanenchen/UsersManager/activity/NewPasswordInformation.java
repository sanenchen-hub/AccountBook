package com.sanenchen.UsersManager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sanenchen.UsersManager.R;

/**
 * 点击FAB后的创建密码Activity
 */
public class NewPasswordInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password_information);
        /*调用方法*/
        setToolBar();
    }

    /**
     * 设置Toolbar
     */
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_new_password_information);
        setSupportActionBar(toolbar);
        /*设置Toolbar上的返回按钮*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 创建ToolBar上的下一步按钮
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_next, menu);
        return true;
    }

    /**
     * 监听ToolBar上的按钮
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://如果点击的是返回按钮
                finish();
                break;
            default:
                return false;
        }
        return true;
    }
}