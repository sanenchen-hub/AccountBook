package com.sanenchen.UsersManager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.tools.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

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
            case R.id.menu_item_toolbar_next:
                /*绑定控件*/
                TextInputLayout password_title = findViewById(R.id.password_title);
                TextInputLayout password_user = findViewById(R.id.password_user);
                TextInputLayout password_password = findViewById(R.id.password_password);
                TextInputLayout password_remark = findViewById(R.id.password_remark);
                TextInputLayout password_url = findViewById(R.id.password_url);
                Boolean testRight = true;//测试项目的规范性
                if (password_title.getEditText().getText().toString().equals("")) {
                    password_title.setError("此项不能为空");
                    testRight = false;
                    if (password_user.getEditText().getText().toString().equals("")) {
                        password_user.setError("此项不能为空");
                        testRight = false;
                        if (password_password.getEditText().getText().toString().equals("")) {
                            password_password.setError("此项不能为空");
                            testRight = false;
                        }
                    }
                }
                if (testRight) {
                    /*获取本地时间*/
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());
                    /*如果该写的都写了后,就录入信息*/
                    DatabaseHelper databaseHelper = new DatabaseHelper(NewPasswordInformation.this,
                            "main.db", null, 1);
                    SQLiteDatabase database = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("title", password_title.getEditText().getText().toString());
                    values.put("user", password_user.getEditText().getText().toString());
                    values.put("password", password_password.getEditText().getText().toString());
                    values.put("remark", password_remark.getEditText().getText().toString());
                    values.put("createTime", simpleDateFormat.format(date));
                    values.put("lookTime", 0);
                    values.put("checkLove", "0");
                    values.put("url", password_url.getEditText().getText().toString());
                    database.insert("PassWordBook", null, values);
                    databaseHelper.close();
                    finish();
                }
                break;
            default:
                return false;
        }
        return true;
    }

}