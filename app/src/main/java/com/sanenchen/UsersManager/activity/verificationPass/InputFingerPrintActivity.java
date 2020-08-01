package com.sanenchen.UsersManager.activity.verificationPass;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.MainActivity;

/**
 * 指纹监听
 * @author sanenchen
 */
public class InputFingerPrintActivity extends AppCompatActivity {
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    ImageView image_finger;//指纹图标
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_finger);
        /*这里调用了指纹识别权限*/
        /*指纹识别*/
        manager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        image_finger = findViewById(R.id.image_finger);

        /*监听密码解锁按钮*/
        Button button_use_password = findViewById(R.id.button_use_password);
        button_use_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InputFingerPrintActivity.this, InputPasswordActivity.class));
                finish();
            }
        });
    }

    /**
     * 为了使每一次都可以指纹解锁，放在这里监听
     */
    @Override
    protected void onResume() {
        super.onResume();
        /*调用方法*/
        startListening(null);
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
                Toast.makeText(InputFingerPrintActivity.this, errString, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            /*认证成功后*/
            startActivity(new Intent(InputFingerPrintActivity.this, MainActivity.class));
            finish();
        }

        @Override
        public void onAuthenticationFailed() {
            /*错误后*/
            /*注意！！这里用到了一个权限：震动权限*/
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_fingerprint_24_red);
            image_finger.setImageDrawable(drawable);
            Vibrator vibrator = (Vibrator) InputFingerPrintActivity.this.getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);//震动提示一下
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);
    }


}
