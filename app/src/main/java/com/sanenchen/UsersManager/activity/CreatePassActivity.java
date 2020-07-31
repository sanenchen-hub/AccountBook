package com.sanenchen.UsersManager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.verificationPass.PassWordActivity;
import com.sanenchen.UsersManager.tools.DatabaseHelper;
import com.sanenchen.UsersManager.tools.SHA224;

/**
 * 设置安全密码
 *
 * @author sanenchen
 * @version v1.0
 */
public class CreatePassActivity extends AppCompatActivity {
    TextInputLayout create_pass_set_pass;
    TextInputLayout create_pass_repeat_pass;
    TextInputLayout create_pass_tips;
    CheckBox check_box_check_finger_print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass);

        /*调用方法*/
        setToolBar();
        createDatabase();
        checkSetPassWord();
        setEditViewListenError();
    }

    /**
     * 设置ToolBar
     */
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_create_pass);
        setSupportActionBar(toolbar);
    }

    /**
     * 判断是否已经设置过密码
     * 如果设置过，直接进入验证界面
     * 没有就不干什么
     */
    private void checkSetPassWord() {
        SharedPreferences sharedPreferences = getSharedPreferences(new SHA224().SHA224("data"), MODE_PRIVATE);
        if (sharedPreferences.getString(new SHA224().SHA224("password"), null) != null) { //判断密码是否为空
            // 如果不为空，说明设置过密码
            /* 启动密码验证界面 */
            startActivity(new Intent(CreatePassActivity.this, PassWordActivity.class));//启动界面
            finish();//关闭这个界面
        } else {
            /*功能配置*/
            //使“指纹验证”CheckBox可用
            check_box_check_finger_print = findViewById(R.id.check_box_check_finger_print);
            if (checkFingerprintSupport()) {
                check_box_check_finger_print.setEnabled(true);
                check_box_check_finger_print.setChecked(true);
            }
        }
    }

    /**
     * 判断是否支持指纹验证
     * 用到的权限：USE_FINGERPRINT
     */
    private Boolean checkFingerprintSupport() {
        FingerprintManager manager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        KeyguardManager mKeyManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        //android studio 上，没有这个会报错
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
                return false;
            }
            //判断硬件是否支持指纹识别
            if (!manager.isHardwareDetected()) {
                Toast.makeText(this, "此手机不支持指纹识别", Toast.LENGTH_SHORT).show();
                return false;
            }
            //判断 是否开启锁屏密码
            if (!mKeyManager.isKeyguardSecure()) {
                Toast.makeText(this, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
                return false;
            }
            //判断是否有指纹录入
            if (!manager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "没有录入指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 监听错误信息
     */
    private void setEditViewListenError() {
        create_pass_tips = findViewById(R.id.create_pass_tips);
        create_pass_set_pass = findViewById(R.id.create_pass_set_pass);
        create_pass_set_pass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                create_pass_set_pass.setCounterEnabled(true);
                if (create_pass_set_pass.getEditText().getText().length() >= 4) {
                    create_pass_set_pass.setErrorEnabled(false);
                } else {
                    create_pass_set_pass.setErrorEnabled(true);
                    create_pass_set_pass.setError("密码需要不小于4位数");
                }
                if (create_pass_repeat_pass.getEditText().getText().toString().equals(
                        create_pass_set_pass.getEditText().getText().toString())) {
                    create_pass_repeat_pass.setErrorEnabled(false);
                }
            }
        });

        create_pass_repeat_pass = findViewById(R.id.create_pass_repeat_pass);
        create_pass_repeat_pass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (create_pass_repeat_pass.getEditText().getText().toString().equals(
                        create_pass_set_pass.getEditText().getText().toString())) {
                    create_pass_repeat_pass.setErrorEnabled(false);
                } else {
                    create_pass_repeat_pass.setErrorEnabled(true);
                    create_pass_repeat_pass.setError("与刚刚的密码不一致");
                }
            }
        });
    }

    /**
     * 创建数据库
     */
    private void createDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(CreatePassActivity.this,
                "main.db", null, 1);
        databaseHelper.getWritableDatabase();
        databaseHelper.close();
    }

    /**
     * 有关ToolBar的按钮
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (create_pass_set_pass.getEditText().getText().length() >= 4 &&
                create_pass_repeat_pass.getEditText().getText().toString()
                        .equals(create_pass_set_pass.getEditText().getText().toString()) &&
                !create_pass_tips.getEditText().getText().toString().equals("")) {

            /*将机密信息加密并写入文件*/
            SharedPreferences.Editor editor = getSharedPreferences(new SHA224().SHA224("data"), MODE_PRIVATE).edit();
            editor.putString(new SHA224().SHA224("password"),
                    new SHA224().SHA224(create_pass_repeat_pass.getEditText().getText().toString()));
            editor.putString(new SHA224().SHA224("pass_word_tips"),
                    create_pass_tips.getEditText().getText().toString());//密码提示就不加密了
            /*判断是否使用了指纹验证*/
            Boolean boolean_check_finger_print = false;
            if (check_box_check_finger_print.isChecked()) {//如果选中
                boolean_check_finger_print = true;
            }
            editor.putBoolean(new SHA224().SHA224("check_finger_print"), boolean_check_finger_print);
            editor.apply();//保存文件

            /* 显示一个对话框 */
            AlertDialog.Builder builder = new AlertDialog
                    .Builder(CreatePassActivity.this)
                    .setTitle("提示")
                    .setMessage("请务必记住刚刚设置的密码，否则您将会永久丢失保存在这里的账户信息！")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();//结束Activity
                            startActivity(new Intent(CreatePassActivity.this, PassWordActivity.class));//启动密码验证
                        }
                    })
                    .setCancelable(false);
            builder.show();
        } else {
            if (create_pass_set_pass.getEditText().getText().length() < 4) {
                create_pass_set_pass.setErrorEnabled(true);
                create_pass_set_pass.setError("密码需要不小于4位数");
            }
            if (!create_pass_repeat_pass.getEditText().getText().toString()
                    .equals(create_pass_set_pass.getEditText().getText().toString())) {
                create_pass_repeat_pass.setErrorEnabled(true);
                create_pass_repeat_pass.setError("与刚刚的密码不一致");
            }
            if (create_pass_tips.getEditText().getText().toString().equals("")) {
                create_pass_tips.setError("此项为必填项");
            }
            Toast.makeText(CreatePassActivity.this, "请您检查下飘红的地方", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}