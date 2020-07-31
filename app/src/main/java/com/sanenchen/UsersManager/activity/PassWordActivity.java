package com.sanenchen.UsersManager.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Vibrator;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordRound.PassWordRound;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordRound.PassWordRoundAdapter;
import com.sanenchen.UsersManager.tools.SHA224;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 输入密码的页面并判断密码正确性
 *
 * @author sanenchen
 * @version v1.0
 */

public class PassWordActivity extends AppCompatActivity implements View.OnClickListener {
    List<PassWordRound> passWordRoundList = new ArrayList<>();
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    ImageView image_finger;//指纹图标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word);
        /*这里调用了指纹识别权限*/
        /*指纹识别*/
        manager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        image_finger = findViewById(R.id.image_finger);

        if (getSharedPreferences(new SHA224().SHA224("data"), MODE_PRIVATE)
                .getBoolean(new SHA224().SHA224("check_finger_print"), false)) {
            /*如果指纹验证可用的话*/
            startListening(null);//启动指纹监听
            image_finger.setVisibility(View.VISIBLE);//显示指纹图标
        }

        /*绑定控件*/
        Button button_number_one = findViewById(R.id.button_number_one);
        Button button_number_two = findViewById(R.id.button_number_two);
        Button button_number_three = findViewById(R.id.button_number_three);
        Button button_number_four = findViewById(R.id.button_number_four);
        Button button_number_five = findViewById(R.id.button_number_five);
        Button button_number_six = findViewById(R.id.button_number_six);
        Button button_number_seven = findViewById(R.id.button_number_seven);
        Button button_number_eight = findViewById(R.id.button_number_eight);
        Button button_number_nine = findViewById(R.id.button_number_nine);
        Button button_number_zero = findViewById(R.id.button_number_zero);
        Button button_number_sharp = findViewById(R.id.button_number_sharp);
        Button button_number_Do = findViewById(R.id.button_number_Do);
        /*监听控件*/
        //原谅我这样写代码......
        button_number_one.setOnClickListener(this);
        button_number_two.setOnClickListener(this);
        button_number_three.setOnClickListener(this);
        button_number_four.setOnClickListener(this);
        button_number_five.setOnClickListener(this);
        button_number_six.setOnClickListener(this);
        button_number_seven.setOnClickListener(this);
        button_number_eight.setOnClickListener(this);
        button_number_nine.setOnClickListener(this);
        button_number_zero.setOnClickListener(this);
        button_number_sharp.setOnClickListener(this);
        button_number_Do.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        /*震动提示一下*/
        /*注意！！这里用到了一个权限：震动权限*/
        Vibrator vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(20);
        /*初始化RecyclerView*/
        final LinearLayoutManager layoutManager = new LinearLayoutManager(PassWordActivity.this);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView_pass_word);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置RecyclerView横向显示
        recyclerView.setLayoutManager(layoutManager);

        Button button = findViewById(v.getId());
        if (!button.getText().toString().equals("删除") && !button.getText().toString().equals("确认")) { // 如果不是确认或者删除键
            PassWordRound passWordRound = new PassWordRound(button.getText().toString());
            passWordRoundList.add(passWordRound);
            /*刷新RecyclerView*/
            PassWordRoundAdapter adapter = new PassWordRoundAdapter(passWordRoundList);
            recyclerView.setAdapter(adapter);
        } else if (passWordRoundList.size() != 0 && button.getText().toString().equals("确认")) {
            String getPass = "";
            String[] allPassWord = new String[passWordRoundList.size()];
            for (int i = 0; i < passWordRoundList.size(); i++) {
                PassWordRound passWordRound = passWordRoundList.get(i);
                allPassWord[i] = passWordRound.getPassWord();
                getPass = getPass + allPassWord[i];
            }

            /* 判断密码正确性 */
            SharedPreferences sharedPreferences = getSharedPreferences(new SHA224().SHA224("data"), MODE_PRIVATE);
            String getOldPass = sharedPreferences.getString(new SHA224().SHA224("password"), null);//获取文件中HASH后的保存的密码
            // 正式判断密码
            if (new SHA224().SHA224(getPass).equals(getOldPass)) { //将加密后的数据与已知加密数据对比
                //密码正确
                finish();//结束Activity
                startActivity(new Intent(PassWordActivity.this, MainActivity.class));//启动主界面
            } else { //密码错误

                Toast.makeText(PassWordActivity.this, "密码错误\n密码提示：" +
                        sharedPreferences.getString(new SHA224().SHA224("pass_word_tips"), null), Toast.LENGTH_SHORT).show();//提示
                passWordRoundList.removeAll(passWordRoundList);//清空所有数据
                /*刷新RecyclerView*/
                PassWordRoundAdapter adapter = new PassWordRoundAdapter(passWordRoundList);
                recyclerView.setAdapter(adapter);
            }
        } else if (passWordRoundList.size() != 0) {
            passWordRoundList.remove(passWordRoundList.size() - 1);
            /*刷新RecyclerView*/
            PassWordRoundAdapter adapter = new PassWordRoundAdapter(passWordRoundList);
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * 指纹验证
     */
    CancellationSignal mCancellationSignal = new CancellationSignal();
    //回调方法
    FingerprintManager.AuthenticationCallback mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
            if (errorCode != 5) {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_fingerprint_24_red);
                image_finger.setImageDrawable(drawable);
                Toast.makeText(PassWordActivity.this, errString, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            /*认证成功后*/
            startActivity(new Intent(PassWordActivity.this, MainActivity.class));
            finish();
        }

        @Override
        public void onAuthenticationFailed() {
            /*错误后*/
            /*注意！！这里用到了一个权限：震动权限*/
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_fingerprint_24_red);
            image_finger.setImageDrawable(drawable);
            Vibrator vibrator = (Vibrator) PassWordActivity.this.getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);//震动提示一下
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);
    }
}