package com.sanenchen.UsersManager.activity.verificationPass;

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
import com.sanenchen.UsersManager.activity.MainActivity;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordRound.PassWordRound;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordRound.PassWordRoundAdapter;
import com.sanenchen.UsersManager.tools.GetSettingThings;
import com.sanenchen.UsersManager.tools.SHA224;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 判断是否支持指纹解锁
 *
 * @author sanenchen
 * @version v1.0
 */

public class PassWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new GetSettingThings(this).checkFingerPrintSetting()) {
            /*如果指纹验证可用的话*/
                startActivity(new Intent(PassWordActivity.this, InputFingerPrintActivity.class));
        } else {
            /*如果指纹验证不可用的话*/
            startActivity(new Intent(PassWordActivity.this, InputPasswordActivity.class));
        }
        finish();
    }
}